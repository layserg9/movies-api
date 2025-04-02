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
            description = movieItem.description ?: ""
        )
    }
}