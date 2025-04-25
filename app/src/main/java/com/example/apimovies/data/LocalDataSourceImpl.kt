package com.example.apimovies.data

import com.example.apimovies.data.local.MovieDao
import com.example.apimovies.data.local.toMovie
import com.example.apimovies.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
): LocalDataSource {
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

    override suspend fun addMovieToFavorites(movie: Movie) {
        movieDao.insertMovie(movie = movie.toEntity())
    }

    override suspend fun removeMovieFromFavorites(movie: Movie) {
        movieDao.delete(movie = movie.toEntity())
    }

    override suspend fun cleanFavorites() {
        movieDao.deleteAll()
    }

    override suspend fun getAllFavorites(): List<Movie> {
        return movieDao.getAll().map {it.toMovie()}
    }

    private fun notifyDataChanged() {
        println("Movie data updated: ${movieDataMap.size} items stored.")
    }
}