package com.example.apimovies.data

import com.example.apimovies.data.local.MovieEntity
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val name: String,
    val alternativeName: String,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Double,
    val description: String,
    val movieLength: String,
    val isFavorite: Boolean = false
) {
    companion object {
        fun init(): Movie = Movie(
            id = 0L,
            name = "",
            alternativeName = "",
            year = 0L,
            genres = listOf(""),
            countries = listOf(""),
            poster = "",
            kpRating = 0.0,
            description = "",
            movieLength = "",
        )

        fun preview(): Movie = Movie(
            id = 1234L,
            name = "Title",
            alternativeName = "Alternative title",
            year = 2025L,
            genres = listOf("action", "horror"),
            countries = listOf("USA", "Mexico"),
            poster = "https://image.openmoviedb.com/kinopoisk-images/4303601/3f89baba-e34d-4526-be68-bbadf0494212/x1000",
            kpRating = 7.5,
            description = "Описание фильма",
            movieLength = "1 ч 35 мин"
        )
    }
}

@Serializable
data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val category: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val moviesCount: Int? = null,
    val cover: String? = null
)

fun Movie.toEntity(): MovieEntity = MovieEntity(
    id = this.id,
    name = this.name,
    alternativeName = this.alternativeName,
    year = this.year,
    genres = this.genres,
    countries = this.countries,
    poster = this.poster,
    kpRating = this.kpRating,
    description = this.description,
    movieLength = this.movieLength,
)