package com.example.apimovies.domain

import com.example.apimovies.data.Categories
import com.example.apimovies.data.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
//    suspend fun searchMovieList(): List<Movie>
//    suspend fun getMovieListByDate(): List<Movie>
     fun getExpectedMovieListFlow(): Flow<List<Movie>>
     suspend fun requestMoviesBySearch(movieName: String): List<Movie>
     suspend fun addMovieToFavorites(movie: Movie)
     suspend fun removeMovieFromFavorites(movie: Movie)
     suspend fun getAllFavorites(): List<Movie>
     suspend fun cleanFavorites()
     suspend fun requestCategories(): List<Categories>
}