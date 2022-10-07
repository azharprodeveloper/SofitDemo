package com.pro.sofitdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Favorite::class], version = 1, exportSchema = false)

abstract class FavDb:RoomDatabase() {
    companion object {
        private var INSTANCE: FavDb? = null
        fun getDatabase(context: Context): FavDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    FavDb::class.java, "fav_db"
                ).build()
            }
            return INSTANCE!!
        }
    }
    abstract fun favDao(): FavDao
}