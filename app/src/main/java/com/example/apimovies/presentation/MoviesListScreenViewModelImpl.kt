package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Movie
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.MoviesListScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListScreenViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MoviesListScreenViewModel, ViewModel() {
    private val _movieList = MutableStateFlow<ImmutableList<Movie>>(persistentListOf())
    private val movieList: StateFlow<ImmutableList<Movie>> = _movieList
    private val _moviesByCategory = mutableStateOf<List<Movie>>(emptyList())


    init {
        loadMovies()
    }

    override val viewState: State<ImmutableList<Movie>>
        @Composable
        get() = movieList.collectAsState(initial = persistentListOf())

    override val moviesByCategoryViewState: State<List<Movie>> = _moviesByCategory

    override fun loadMoviesByCategory(slug: String) {
        viewModelScope.launch {
            val result = movieRepository.requestMoviesByCategory(slug = slug, limit = 250)
            _moviesByCategory.value = result
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val movies = movieRepository.requestMoviesByCategory(
                slug = "planned-to-watch-films",
                limit = 250
            )
            _movieList.value = movies.filter { it.poster.isNotBlank() }.toImmutableList()
        }
    }
}