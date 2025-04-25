package com.example.apimovies.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface MovieDao {
    @Query("SELECT * FROM movieentity")
    fun getAll(): List<MovieEntity>

    @Insert
    fun insertAll(vararg movies: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)
}