package com.pro.sofitdemo.retrofit

import com.pro.sofitdemo.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val drinkApi: DrinkApi by lazy {
        retrofit.create(DrinkApi::class.java)
    }
}