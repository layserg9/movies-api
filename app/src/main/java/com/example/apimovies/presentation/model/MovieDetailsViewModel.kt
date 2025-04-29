package com.example.apimovies.presentation.model

import androidx.compose.runtime.Stable
import com.example.apimovies.data.Movie
import kotlinx.coroutines.flow.StateFlow

@Stable
interface MovieDetailsViewModel {
    val favoriteIds: StateFlow<Set<Long>>

    fun onFavoriteClicked(movie: Movie)
    fun isFavorite(movieId: Long): Boolean
}