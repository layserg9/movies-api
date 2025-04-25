package com.example.apimovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val alternativeName: String,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Double,
    val description: String,
)
