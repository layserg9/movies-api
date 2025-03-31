package com.example.apimovies.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    val id: Long,
    val name: String,
    val alternativeName: String?,
    val year: Long,
    val genres: List<Genre>,
    val countries: List<Country>,
    val poster: Poster,
    val rating: Rating,
    val movieLength: Int,
    val isSeries: Boolean,
    val ageRating: Int?,
    val status: String,
    val votes: Rating
)

@Serializable
data class Rating(
    val kp: Double? = null,
    val imdb: Double? = null,
    val filmCritics: Double? = null,
    val russianFilmCritics: Double? = null,
    val await: Double? = null
)

@Serializable
data class Genre(
    val name: String
)

@Serializable
data class Country(
    val name: String
)

@Serializable
data class  Poster(
    val url: String,
    val previewUrl: String
)