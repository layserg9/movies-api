package com.example.apimovies.data

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Long,
    val name: String?,
    val alternativeName: String?,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Double?,
) {
    companion object {
        fun init(): Movie = Movie(
            id = 0L,
            name = "",
            alternativeName = "",
            year = 0L,
            genres = listOf(),
            countries = listOf(),
            poster = "",
            kpRating = 0.0,
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
        )
    }
}



