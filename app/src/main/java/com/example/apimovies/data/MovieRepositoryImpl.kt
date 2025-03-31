package com.example.apimovies.data

import com.example.apimovies.domain.MovieRepository
import com.example.apimovies.retrofit.AuthInterceptor
import com.example.apimovies.retrofit.MovieAPI
import com.example.apimovies.retrofit.MovieItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(

): MovieRepository{
    override fun getMovieList(): List<Movie> {
        return emptyList()
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MovieAPI::class.java)

    private suspend fun fetchMovie(movieId: Long): MovieItem {
        return apiService.getMovieById(movieId)
    }
}
