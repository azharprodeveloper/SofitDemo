package com.pro.sofitdemo.utils

import android.content.Context
import android.content.SharedPreferences


class MyPreferences() {

    var instance: MyPreferences? = null
    public fun getPrefInstance(): MyPreferences? {
        if (instance == null) {
            instance = MyPreferences()
        }
        return instance
    }
    fun getAlphabetString(context:Context): String? {
        val sharedPref = context.getSharedPreferences("stringAlphabet", Context.MODE_PRIVATE)
        return sharedPref.getString("stringAlphabet", "a")
    }
    fun setAlphabetString(context: Context,string:String){
        val sharedPref = context.getSharedPreferences("stringAlphabet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("stringAlphabet", string)
        editor.apply()
    }
    fun getNameString(context:Context): String? {
        val sharedPref = context.getSharedPreferences("stringName", Context.MODE_PRIVATE)
        return sharedPref.getString("stringName", "margarita")
    }
    fun setNameString(context: Context,string:String){
        val sharedPref = context.getSharedPreferences("stringName", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("stringName", string)
        editor.apply()
    }
    fun getUserState(context:Context): Boolean {
        val sharedPref = context.getSharedPreferences("search", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("search", false)
    }
    fun setUserState(context: Context,bol:Boolean){
        val sharedPref = context.getSharedPreferences("search", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("search", bol)
        editor.apply()
    }
}