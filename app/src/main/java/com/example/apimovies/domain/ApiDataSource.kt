package com.example.apimovies.domain

import com.example.apimovies.data.Categories
import com.example.apimovies.data.Movie

interface ApiDataSource {
    suspend fun requestExpectedMovies(): List<Movie>
    suspend fun requestAllExpectedMovies(): List<Movie>
    suspend fun requestMoviesBySearch(movieName: String): List<Movie>
    suspend fun requestMoviesCategories(): List<Categories>
}