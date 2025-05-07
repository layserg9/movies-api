package com.example.apimovies.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import kotlinx.collections.immutable.ImmutableList

@Stable
interface MainListScreenViewModel {
    @get:Composable
    val moviesViewState: State<ImmutableList<Movie>>
    @get:Composable
    val categoriesViewState: State<List<Category>>
}