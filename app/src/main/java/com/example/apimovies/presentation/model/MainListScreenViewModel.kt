package com.example.apimovies.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import com.example.apimovies.data.local.MoviesListType

@Stable
interface MainListScreenViewModel {
    @get:Composable
    val categoriesViewState: State<List<Category>>
    @get:Composable
    val moviesMapState: State<Map<MoviesListType, List<Movie>>>
}