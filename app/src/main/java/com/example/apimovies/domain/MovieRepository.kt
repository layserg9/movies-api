package com.example.apimovies.domain

import com.example.apimovies.data.Movie

interface MovieRepository {
    suspend fun searchMovieList(): List<Movie>
    suspend fun getMovieListByDate(): List<Movie>
    suspend fun getMovieList(): List<Movie>
}