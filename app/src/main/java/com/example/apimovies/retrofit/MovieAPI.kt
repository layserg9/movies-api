package com.example.apimovies.retrofit

import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("v1.4/movie/{id}")
    suspend fun getMovieById(@Path("id") movieId: Long): MovieItem

    @GET("v1.4/movie/search")
    suspend fun getMoviesByName(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("query") movieName: String
    ): MovieResponse

    @GET("v1.4/movie")
    suspend fun getMoviesByDate(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("updatedAt") updatedAt: String
    ): MovieResponse

    @GET("v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int? = 1,
        @Query("limit") limit: Int? = 10,
        @Query("lists") lists: String
    ): MovieResponse

    @GET("v1.4/list")
    suspend fun getMoviesCategories(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
    ): CategoriesResponse
}