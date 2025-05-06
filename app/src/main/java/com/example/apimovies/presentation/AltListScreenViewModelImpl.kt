package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Categories
import com.example.apimovies.data.Movie
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.AltListScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AltListScreenViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : AltListScreenViewModel, ViewModel() {
    private val _movieList = MutableStateFlow<ImmutableList<Movie>>(persistentListOf())
    private val movieList: StateFlow<ImmutableList<Movie>> = _movieList
    private val _categoriesList = MutableStateFlow<List<Categories>>(emptyList())
    private val categoriesList: StateFlow<List<Categories>> = _categoriesList


    init {
        loadMovies()
        loadCategories()
    }

    override val moviesViewState: State<ImmutableList<Movie>>
        @Composable
        get() = movieList.collectAsState(initial = persistentListOf())
    override val categoriesViewState: State<List<Categories>>
        @Composable
        get() = categoriesList.collectAsState()

    private fun loadMovies() {
        viewModelScope.launch {
            movieRepository.getExpectedMovieListFlow().collect { movies ->
                _movieList.value = movies.filter { it.poster.isNotBlank() }.toImmutableList()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesList.value = movieRepository.requestCategories(limit = 20)
        }
    }
}