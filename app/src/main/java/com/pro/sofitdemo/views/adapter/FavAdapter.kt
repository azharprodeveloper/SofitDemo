package com.pro.sofitdemo.views.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pro.sofitdemo.R
import com.pro.sofitdemo.models.Drink
import com.pro.sofitdemo.room.FavDb
import com.pro.sofitdemo.room.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavAdapter: RecyclerView.Adapter<FavAdapter.MyViewHolder>() {
    val favList: MutableList<Favorite> = arrayListOf()

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
        holder.alcoholCheck.isChecked = favList[position].drink.strAlcoholic.equals("Alcoholic")
        Glide.with(holder.itemView.context).load(favList[position].drink.strDrinkThumb)
            .into(holder.thumbnail)
        holder.drinkName.text = favList[position].drink.strDrink
        holder.drinkDescription.text = favList[position].drink.strInstructions
        holder.favCheck.setImageResource(R.drawable.fav_ic)
        holder.favCheck.setColorFilter(Color.YELLOW)
        holder.favCheck.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                favList[position].id?.let { it -> FavDb.getDatabase(holder.itemView.context).favDao().deleteFav(it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun setData(list: MutableList<Favorite>) {
        this.favList.clear()
        this.favList.addAll(list)
        notifyDataSetChanged()
    }
}