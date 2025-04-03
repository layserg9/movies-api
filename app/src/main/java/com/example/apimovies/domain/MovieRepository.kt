package com.example.apimovies.domain

import com.example.apimovies.data.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
//    suspend fun searchMovieList(): List<Movie>
//    suspend fun getMovieListByDate(): List<Movie>
     fun getExpectedMovieListFlow(): Flow<List<Movie>>
     suspend fun requestMoviesBySearch(movieName: String): List<Movie>
}