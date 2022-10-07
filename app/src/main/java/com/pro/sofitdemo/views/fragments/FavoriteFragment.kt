package com.pro.sofitdemo.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pro.sofitdemo.MainActivity
import com.pro.sofitdemo.databinding.FragmentFavoriteBinding
import com.pro.sofitdemo.views.adapter.FavAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() {
    val favAdapter by lazy { FavAdapter() }
    lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        binding.favRec.adapter = favAdapter
        initObserver()
        return binding.root
    }

    fun initObserver() {
        activity?.let {
            if (it is MainActivity) {
                    it.drinkViewModel.readAllFav().observe(viewLifecycleOwner) {
                        Log.i("testingtag", "initObserver:${it.size} ")
                        if(it.isNotEmpty()){
                           favAdapter.setData(it)
                        }
                        else if(it.isEmpty()){
                            favAdapter.setData(it)
                        }
                    }
            }
        }
    }
}