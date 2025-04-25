package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Movie
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.MovieDetailsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieDetailsViewModel, ViewModel() {
    private val _movieList = MutableStateFlow<ImmutableList<Movie>>(persistentListOf())
    private val movieList: StateFlow<ImmutableList<Movie>> = _movieList

    init {
        loadMovies()
    }

    override val viewState: State<ImmutableList<Movie>>
        @Composable
        get() = movieList.collectAsState(initial = persistentListOf())

    override fun addToFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovieToFavorites(movie)
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val favorites = movieRepository.getAllFavorites()
            _movieList.value = favorites.toPersistentList()
        }
    }
}