package com.pro.sofitdemo.retrofit

import com.pro.sofitdemo.models.drinks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkApi {

    @GET("search.php")
    suspend fun getDrinksByName(@Query("s") drinkName: String): Response<drinks>

    @GET("search.php")
    suspend fun getDrinksByAlphabet(@Query("f") alphabet: String): Response<drinks>
}