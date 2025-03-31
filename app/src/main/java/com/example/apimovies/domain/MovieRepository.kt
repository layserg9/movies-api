package com.example.apimovies.domain

import com.example.apimovies.data.Movie

interface MovieRepository {
    fun getMovieList(): List<Movie>
}