package com.example.apimovies.domain

import com.example.apimovies.data.Categories
import com.example.apimovies.data.Movie

interface ApiDataSource {
    suspend fun requestListMovies(limit: Int, list: String): List<Movie>
    suspend fun requestMoviesBySearch(movieName: String): List<Movie>
    suspend fun requestMoviesCategories(limit: Int, category: String): List<Categories>
}