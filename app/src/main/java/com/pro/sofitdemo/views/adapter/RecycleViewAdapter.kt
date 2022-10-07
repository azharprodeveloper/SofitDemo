package com.pro.sofitdemo.views.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pro.sofitdemo.R
import com.pro.sofitdemo.models.Drink
import com.pro.sofitdemo.room.FavDb
import com.pro.sofitdemo.room.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>() {
    val drinkList: MutableList<Drink> = arrayListOf()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById<ImageFilterView>(R.id.thumbnail)
        val drinkName = itemView.findViewById<TextView>(R.id.drink_title)
        val drinkDescription = itemView.findViewById<TextView>(R.id.drink_description)
        val favCheck = itemView.findViewById<ImageView>(R.id.fav)
        val alcoholCheck = itemView.findViewById<CheckBox>(R.id.alcoholic_check)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.alcoholCheck.isChecked = drinkList[position].strAlcoholic.equals("Alcoholic")
        Glide.with(holder.itemView.context).load(drinkList[position].strDrinkThumb)
            .into(holder.thumbnail)
        holder.drinkName.text = drinkList[position].strDrink
        holder.drinkDescription.text = drinkList[position].strInstructions
        CoroutineScope(IO).launch {
            val isExist = drinkList[position].idDrink?.let {
                FavDb.getDatabase(holder.itemView.context).favDao().isExist(it)
            }
            if (isExist == true) {
                withContext(Main) {
                    holder.favCheck.setImageResource(R.drawable.fav_ic)
                    holder.favCheck.setColorFilter(Color.YELLOW)
                }
            } else if (isExist == false) {
                withContext(Main) {
                    holder.favCheck.setImageResource(R.drawable.star)
                    holder.favCheck.setColorFilter(Color.BLACK)
                }
            }
        }
        holder.favCheck.setOnClickListener {
            CoroutineScope(IO).launch {
                val isExist = drinkList[position].idDrink?.let {
                    FavDb.getDatabase(holder.itemView.context).favDao().isExist(it)
                }
                if (isExist == true) {
                    drinkList[position].idDrink?.let {
                        FavDb.getDatabase(holder.itemView.context).favDao().deleteFromDrinkId(it)
                    }

                } else if (isExist == false) {
                    drinkList[position].idDrink?.let {
                        FavDb.getDatabase(holder.itemView.context).favDao()
                            .insert(Favorite(null, it, drinkList[position]))
                    }
                }
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    fun setData(list: MutableList<Drink>) {
        this.drinkList.clear()
        this.drinkList.addAll(list)
        notifyDataSetChanged()
    }
}