package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import com.example.apimovies.data.local.MoviesListType
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.MainListScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainListScreenViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MainListScreenViewModel, ViewModel() {
    private val _categoriesList = MutableStateFlow<List<Category>>(emptyList())
    private val categoriesList: StateFlow<List<Category>> = _categoriesList
    private val _moviesMap = mutableStateOf<Map<MoviesListType, List<Movie>>>(emptyMap())

    init {
        loadCategories()
        loadAllCategories()
    }

    override val moviesMapState: State<Map<MoviesListType, List<Movie>>>
        @Composable
        get() = _moviesMap

    override val categoriesViewState: State<List<Category>>
        @Composable
        get() = categoriesList.collectAsState()

    private fun loadAllCategories() {
        viewModelScope.launch {
            val result = MoviesListType.entries.associateWith { type ->
                movieRepository.requestMoviesByCategory(type.getSlug(), limit = 20)
            }
            _moviesMap.value = result
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesList.value = movieRepository.requestCategories(limit = 20)
        }
    }
}