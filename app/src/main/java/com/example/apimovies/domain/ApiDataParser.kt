package com.example.apimovies.domain

import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import com.example.apimovies.retrofit.CategoriesItem
import com.example.apimovies.retrofit.MovieItem

interface ApiDataParser {
    fun parseMovie(movieItem: MovieItem): Movie
    fun parseCategories(categories: CategoriesItem): Category
}