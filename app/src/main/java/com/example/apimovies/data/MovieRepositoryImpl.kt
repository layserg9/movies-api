package com.example.apimovies.data

import android.content.SharedPreferences
import android.util.Log
import com.example.apimovies.domain.ApiDataSource
import com.example.apimovies.domain.LocalDataSource
import com.example.apimovies.domain.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferences: SharedPreferences,
) : MovieRepository {
    companion object {
        private const val LAST_UPDATE_TIME_KEY = "last_update_time"
    }

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        repositoryScope.launch {
            refreshExpectedMovieListIfNeeded()
        }
    }

    override fun getExpectedMovieListFlow(): Flow<List<Movie>> {
        return localDataSource.getExpectedMovieListFlow()
    }

    override suspend fun requestCategories(limit: Int): List<Category> {
        Log.d("MovieRepository", "Requesting Categories")
        return apiDataSource.requestMoviesCategories(limit = limit, category = "Онлайн-кинотеатр")
    }

    override suspend fun requestMoviesByCategory(slug: String, limit: Int): List<Movie> {
        Log.d("MovieRepository", "Requesting movies by Categoy")
        return apiDataSource.requestListMovies(limit = limit, list = slug)
    }

    override suspend fun requestMoviesBySearch(movieName: String): List<Movie> {
        Log.d("MovieRepository", "Requesting movie by name")
        return apiDataSource.requestMoviesBySearch(movieName = movieName)
    }

    private suspend fun refreshExpectedMovieListIfNeeded() {
        val currentMovies = localDataSource.getExpectedMovieListFlow().first()
        val lastUpdateTime = getLastUpdateTime()
        if (currentMovies.isEmpty() || System.currentTimeMillis() - lastUpdateTime > 24 * 60 * 60 * 1000) {
            Log.d("MovieRepository", "refreshExpectedMovieListIfNeeded")
            val movies = requestMoviesByCategory(slug = "planned-to-watch-films", limit = 50)
            localDataSource.storeMovies(movies)
            saveLastUpdateTime(System.currentTimeMillis())
        }
    }

    private fun getLastUpdateTime(): Long {
        return sharedPreferences.getLong(LAST_UPDATE_TIME_KEY, 0L)
    }

    private fun saveLastUpdateTime(time: Long) {
        sharedPreferences.edit().putLong(LAST_UPDATE_TIME_KEY, time).apply()
    }

    override suspend fun addMovieToFavorites(movie: Movie) {
        localDataSource.addMovieToFavorites(movie)
    }

    override suspend fun removeMovieFromFavorites(movie: Movie) {
        localDataSource.removeMovieFromFavorites(movie)
    }

    override suspend fun cleanFavorites() {
        localDataSource.cleanFavorites()
    }

    override suspend fun getAllFavorites(): List<Movie> {
        return localDataSource.getAllFavorites()
    }
}
