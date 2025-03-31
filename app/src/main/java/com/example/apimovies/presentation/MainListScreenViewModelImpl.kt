package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.apimovies.data.Movie
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.MainListScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

@HiltViewModel
class MainListScreenViewModelImpl @Inject constructor(
    val movieRepository: MovieRepository
): MainListScreenViewModel, ViewModel() {
    override val viewState: State<ImmutableList<Movie>>
        @Composable
        get() = TODO("Not yet implemented")
}