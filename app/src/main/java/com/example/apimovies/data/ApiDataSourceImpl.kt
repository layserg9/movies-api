package com.example.apimovies.data

import android.util.Log
import com.example.apimovies.domain.ApiDataParser
import com.example.apimovies.domain.ApiDataSource
import com.example.apimovies.retrofit.AuthInterceptor
import com.example.apimovies.retrofit.MovieAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiDataSourceImpl @Inject constructor(
    private val apiDataParser: ApiDataParser
) : ApiDataSource {
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

    override suspend fun requestListMovies(limit: Int, list: String): List<Movie> {
        return try {
            val response = apiService.getMovies(limit = limit, lists = list).docs
            response.map { movieItem ->
                apiDataParser.parseMovie(movieItem)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun requestMoviesCategories(limit: Int, category: String): List<Category> {
        return try {
            val response = apiService.getMoviesCategories(limit = limit, category = category).docs
            response.map { categoriesItem ->
                Log.d("MovieRepository", "requestMoviesCategories: $categoriesItem")
                apiDataParser.parseCategories(categoriesItem)
            }
        } catch (e: Exception) {
            Log.d("MovieRepository", "Exception categories request: $e")
            emptyList()
        }
    }

    override suspend fun requestMoviesBySearch(movieName: String): List<Movie> {
        return try{
            val response = apiService.getMoviesByName(movieName = movieName).docs
            response.map{ movieItem ->
                apiDataParser.parseMovie(movieItem)
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Network error", e)
            emptyList()
        }
    }
}