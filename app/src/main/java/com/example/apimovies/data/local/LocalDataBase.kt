package com.example.apimovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database (entities = [MovieEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class LocalDataBase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}
