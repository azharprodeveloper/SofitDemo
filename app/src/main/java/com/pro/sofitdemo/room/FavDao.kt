package com.pro.sofitdemo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavDao {
    @Insert
    fun insert(favorite: Favorite)

    @Query("DELETE FROM fav_table WHERE id = :id")
    fun deleteFav(id: Int)

    @Query("DELETE FROM fav_table WHERE drinkId = :drinkId")
    fun deleteFromDrinkId(drinkId: String)

    @Query("SELECT * FROM fav_table ORDER BY id DESC")
    fun getAllFavorite(): LiveData<MutableList<Favorite>>

    @Query("SELECT EXISTS(SELECT * FROM fav_table WHERE drinkId = :drinkId)")
    fun isExist(drinkId: String): Boolean?
}