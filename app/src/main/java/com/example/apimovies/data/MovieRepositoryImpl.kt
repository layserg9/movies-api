package com.example.apimovies.data

import com.example.apimovies.domain.ApiDataSource
import com.example.apimovies.domain.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource
) : MovieRepository {


//    override suspend fun getMovieList(): List<Movie> {
//        return try {
//            val movieIds = listOf(333L, 5619L, 6695L)
//            movieIds.map { fetchMovie(it).toMovie() }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

    override suspend fun getExpectedMovieList(): List<Movie> {
        return apiDataSource.getExpectedMovies()
    }

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
