package com.example.apimovies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apimovies.data.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val alternativeName: String,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Double,
    val description: String,
)

fun MovieEntity.toMovie(): Movie = Movie(
    id = this.id,
    name = this.name,
    alternativeName = this.alternativeName,
    year = this.year,
    genres = this.genres,
    countries = this.countries,
    poster = this.poster,
    kpRating = this.kpRating,
    description = this.description,
)
