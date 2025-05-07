package com.example.apimovies.domain

import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getExpectedMovieListFlow(): Flow<List<Movie>>
     suspend fun addMovieToFavorites(movie: Movie)
     suspend fun removeMovieFromFavorites(movie: Movie)
     suspend fun getAllFavorites(): List<Movie>
     suspend fun cleanFavorites()
     suspend fun requestCategories(limit: Int): List<Category>
     suspend fun requestMoviesByCategory(slug: String, limit: Int): List<Movie>
     suspend fun requestMoviesBySearch(movieName: String): List<Movie>
}