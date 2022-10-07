package com.pro.sofitdemo.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pro.sofitdemo.repository.Repository
import com.pro.sofitdemo.viewmodel.DrinkViewModel

class MainViewModelFactory(private val repository:Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DrinkViewModel(repository) as T
    }
}