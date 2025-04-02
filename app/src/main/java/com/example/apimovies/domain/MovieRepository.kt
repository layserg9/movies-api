package com.example.apimovies.domain

import com.example.apimovies.data.Movie

interface MovieRepository {
    suspend fun getMovieList(): List<Movie>
}