package com.example.apimovies.retrofit
import retrofit2.http.Path
import retrofit2.http.GET

interface MovieAPI {
    @GET("v1.4/movie/{id}")
    suspend fun getMovieById(@Path("id") movieId: Long): MovieItem
}