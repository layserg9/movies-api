package com.example.apimovies.data

import android.util.Log
import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.retrofit.AuthInterceptor
import com.example.apimovies.retrofit.MovieAPI
import com.example.apimovies.retrofit.MovieItem
import com.example.apimovies.retrofit.MovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(

) : MovieRepository {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(MovieAPI::class.java)

//    override suspend fun getMovieList(): List<Movie> {
//        return try {
//            val movieIds = listOf(333L, 5619L, 6695L)
//            movieIds.map { fetchMovie(it).toMovie() }
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

    override suspend fun getMovieList(): List<Movie> {
        return try {
            val movies = fetchMovies("star")
            movies.map { it.toMovie() }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error fetching movie list", e)
            emptyList()
        }
    }

    private suspend fun fetchMovies(movieName: String): List<MovieItem> {
        return try {
            apiService.getMoviesByName(movieName = movieName).docs
        } catch (e: Exception) {
            Log.e("MovieRepository", "Network error", e)
            emptyList()
        }
    }

    private suspend fun fetchMovie(movieId: Long): MovieItem {
        return apiService.getMovieById(movieId)
    }

    private fun MovieItem.toMovie(): Movie {
        return Movie(
            id = this.id,
            name = this.name?: "",
            alternativeName = this.alternativeName?: "",
            year = this.year,
            genres = this.genres.map { it.name },
            countries = this.countries.map { it.name },
            poster = this.poster?.url,
            kpRating = this.rating?.kp
        )
    }
}
