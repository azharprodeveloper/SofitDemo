package com.pro.sofitdemo.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pro.sofitdemo.models.Drink

@Entity(tableName = "fav_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val drinkId:String,
    @Embedded
    val drink:Drink
)
