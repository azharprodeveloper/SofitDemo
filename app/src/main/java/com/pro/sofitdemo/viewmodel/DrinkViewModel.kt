package com.pro.sofitdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pro.sofitdemo.models.drinks
import com.pro.sofitdemo.repository.Repository
import com.pro.sofitdemo.room.Favorite
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response

class DrinkViewModel(private val repository: Repository) : ViewModel(){
    val drinkResponse: MutableLiveData<Response<drinks>> = MutableLiveData()
    val drinkResponse2: MutableLiveData<Response<drinks>> = MutableLiveData()
    val homeUpdater:MutableLiveData<Boolean> = MutableLiveData(false)

    fun getDrinksByName(drinkName: String) {
        viewModelScope.launch {
            val response = repository.getDrinksByName(drinkName)
            drinkResponse.value = response
        }
    }

    fun getDrinksByAlphabet(alphabet: String) {
        viewModelScope.launch {
            val response = repository.getDrinksByAlphabet(alphabet)
            drinkResponse2.value = response
        }
    }

    fun insertFav(fav: Favorite) {
        viewModelScope.launch {
            repository.insertFav(fav)
        }
    }

    fun deleteFav(id: Int) {
        viewModelScope.launch {
            repository.deleteFav(id)
        }
    }

    fun readAllFav(): LiveData<MutableList<Favorite>> {
        return repository.readAllFav()
    }
}