package com.example.apimovies.domain

import com.example.apimovies.data.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getExpectedMovieListFlow(): Flow<List<Movie>>
    fun storeMovies(movies: List<Movie>)
}