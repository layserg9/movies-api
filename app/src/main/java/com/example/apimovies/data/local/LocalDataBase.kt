package com.example.apimovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [MovieEntity::class], version = 1)
abstract class LocalDataBase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}
