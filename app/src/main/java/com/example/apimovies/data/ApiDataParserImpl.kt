package com.example.apimovies.data

import com.example.apimovies.domain.ApiDataParser
import com.example.apimovies.retrofit.MovieItem
import javax.inject.Inject

class ApiDataParserImpl @Inject constructor(): ApiDataParser {
    override fun parseMovie(movieItem: MovieItem): Movie {
        return Movie(
            id = movieItem.id ?: 0L,
            name = movieItem.name ?: "",
            alternativeName = movieItem.alternativeName ?: "",
            year = movieItem.year ?: 0L,
            genres = movieItem.genres?.take(2)?.map { it.name ?: "" } ?: listOf(),
            countries = movieItem.countries?.take(1)?.map { it.name ?: "" } ?: listOf(),
            poster = movieItem.poster?.url ?: "",
            kpRating = movieItem.rating?.kp ?: 0.0,
            description = movieItem.description ?: "",
            movieLength = formatMovieLength(movieItem.movieLength)
        )
    }

    private fun formatMovieLength(minutes: Int?): String {
        if (minutes == null || minutes <= 0) return ""

        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return buildString {
            if (hours > 0) append("$hours ч ")
            if (remainingMinutes > 0) append("$remainingMinutes мин")
        }.trim()
    }
}