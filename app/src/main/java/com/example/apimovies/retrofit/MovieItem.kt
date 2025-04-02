package com.example.apimovies.retrofit

import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    val id: Long?,
    val name: String?,
    val alternativeName: String?,
    val year: Long?,
    val genres: List<Genre>?,
    val countries: List<Country>?,
    val poster: Poster?,
    val rating: Rating?,
    val movieLength: Int?,
    val isSeries: Boolean?,
    val ageRating: Int?,
    val status: String?,
    val votes: Rating?
)

@Serializable
data class Rating(
    val kp: Double?,
    val imdb: Double?,
    val filmCritics: Double?,
    val russianFilmCritics: Double?,
    val await: Double?
)

@Serializable
data class Genre(
    val name: String?
)

@Serializable
data class Country(
    val name: String?
)

@Serializable
data class Poster(
    val url: String?,
    val previewUrl: String?
)

@Serializable
data class MovieResponse(
    val docs: List<MovieItem>
)