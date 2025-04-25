package com.example.apimovies.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.example.apimovies.data.local.LocalDataBase
import com.example.apimovies.data.local.MovieEntity
import com.example.apimovies.domain.ApiDataSource
import com.example.apimovies.domain.LocalDataSource
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.retrofit.MovieItem
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
    private val sharedPreferences: SharedPreferences
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

    private suspend fun requestExpectedMovieList(): List<Movie> {
        Log.d("MovieRepository", "Requesting expected movie list")
        return apiDataSource.requestExpectedMovies()
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
            val movies = requestExpectedMovieList()
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

    private fun test(items: List<MovieEntity>, context: Context){
        val db = Room.databaseBuilder(
            context = context,
            LocalDataBase::class.java, "database-name"
        ).build()

        db.movieDao().insertAll(*items.toTypedArray())
    }

    //    override suspend fun getMovieList(): List<Movie> {
//        return try {
//            val movieIds = listOf(333L, 5619L, 6695L)
//            movieIds.map { fetchMovie(it).toMovie() }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

//    override suspend fun getMovieListByDate(): List<Movie> {
//        return try {
//            val movies = fetchMoviesByDate("01.01.2025-01.04.2025")
//            movies.map { it.toMovie() }
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "Error fetching movie list", e)
//            emptyList()
//        }
//    }

//    override suspend fun searchMovieList(): List<Movie> {
//        return try {
//            val movies = fetchMoviesByName("star wars")
//            movies.map { it.toMovie() }
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "Error fetching movie list", e)
//            emptyList()
//        }
//    }
//
//    private suspend fun fetchMoviesByDate(updatedAt: String): List<MovieItem> {
//        return try {
//            apiService.getMoviesByDate(limit = 30, updatedAt = updatedAt).docs
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "Network error", e)
//            emptyList()
//        }
//    }
//
//
//    private suspend fun fetchMoviesByName(movieName: String): List<MovieItem> {
//        return try {
//            apiService.getMoviesByName(limit = 30, movieName = movieName).docs
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "Network error", e)
//            emptyList()
//        }
//    }
//
//    private suspend fun fetchMovie(movieId: Long): MovieItem {
//        return apiService.getMovieById(movieId)
//    }
//
}
