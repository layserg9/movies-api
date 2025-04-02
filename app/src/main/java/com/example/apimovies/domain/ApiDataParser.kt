package com.example.apimovies.domain

import com.example.apimovies.data.Movie
import com.example.apimovies.retrofit.MovieItem

interface ApiDataParser {
    fun parseMovie(movieItem: MovieItem): Movie
}