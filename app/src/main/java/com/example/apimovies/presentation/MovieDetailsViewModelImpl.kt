package com.example.apimovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apimovies.data.Movie
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.presentation.model.MovieDetailsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModelImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieDetailsViewModel, ViewModel() {
    private val _favoriteIds = MutableStateFlow<Set<Long>>(emptySet())

    override val favoriteIds: StateFlow<Set<Long>> = _favoriteIds

    init {
        loadMovies()
    }

    override fun onFavoriteClicked(movie: Movie) {
        if (isFavorite(movie.id)) removeFromFavorites(movie) else addToFavorites(movie)
    }

    override fun isFavorite(movieId: Long): Boolean {
        return favoriteIds.value.contains(movieId)
    }

    private fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            movieRepository.addMovieToFavorites(movie)
            _favoriteIds.update { it + movie.id }
        }
    }

    private fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            movieRepository.removeMovieFromFavorites(movie)
            _favoriteIds.update { it - movie.id }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val favorites = movieRepository.getAllFavorites()
            _favoriteIds.value = favorites.map { it.id }.toSet()
        }
    }
}