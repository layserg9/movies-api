package com.example.apimovies.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Category
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.CategoriesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : CategoriesViewModel, ViewModel() {

    private val _categoriesList = MutableStateFlow<List<Category>>(emptyList())
    private val categoriesList: StateFlow<List<Category>> = _categoriesList

    init {
        loadCategories()
    }

    override val viewState: State<List<Category>>
        @Composable
        get() = categoriesList.collectAsState()

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesList.value = movieRepository.requestCategories(limit = 250)
        }
    }
}