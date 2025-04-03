package com.example.apimovies.data

import com.example.apimovies.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(): LocalDataSource {
    private val expectedMoviesFlow = MutableStateFlow<List<Movie>>(emptyList())
    private val movieDataMap = mutableMapOf<Long, Movie>()

    override fun getExpectedMovieListFlow(): Flow<List<Movie>> {
        return expectedMoviesFlow
    }

    override fun storeMovies(movies: List<Movie>) {
        movies.associateBy { it.id }.let(movieDataMap::putAll)
        expectedMoviesFlow.value = movieDataMap.values.toList()
        notifyDataChanged()
    }

    private fun notifyDataChanged() {
        println("Movie data updated: ${movieDataMap.size} items stored.")
    }
}