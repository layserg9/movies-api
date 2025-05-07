package com.example.apimovies.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.example.apimovies.data.Movie
import kotlinx.collections.immutable.ImmutableList

@Stable
interface MoviesListScreenViewModel{
//    @get:Composable
//    val viewState: State<ImmutableList<Movie>>
    val moviesByCategoryViewState: State<List<Movie>>
    fun loadMoviesByCategory(slug: String)
}