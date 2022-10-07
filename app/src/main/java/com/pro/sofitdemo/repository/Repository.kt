package com.pro.sofitdemo.repository

import androidx.lifecycle.LiveData
import com.pro.sofitdemo.models.drinks
import com.pro.sofitdemo.retrofit.RetrofitInstance
import com.pro.sofitdemo.room.FavDao
import com.pro.sofitdemo.room.Favorite
import retrofit2.Response

class Repository(private var favDao: FavDao) {
    suspend fun getDrinksByName(drinkName: String): Response<drinks>? {
        return RetrofitInstance.drinkApi.getDrinksByName(drinkName)
    }

    suspend fun getDrinksByAlphabet(alphabet: String): Response<drinks> {
        return RetrofitInstance.drinkApi.getDrinksByAlphabet(alphabet)
    }
     fun insertFav(fav:Favorite){
        favDao.insert(fav)
    }
    fun deleteFav(id: Int){
        favDao.deleteFav(id)
    }
    fun readAllFav():LiveData<MutableList<Favorite>>{
        return favDao.getAllFavorite()
    }
}