package com.example.apimovies.di

import com.example.apimovies.domain.ApiDataParser
import com.example.apimovies.data.ApiDataParserImpl
import com.example.apimovies.domain.ApiDataSource
import com.example.apimovies.data.ApiDataSourceImpl
import com.example.apimovies.data.LocalDataSourceImpl
import com.example.apimovies.data.MovieRepositoryImpl
import com.example.apimovies.domain.LocalDataSource
import com.example.apimovies.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindApiDataSource(dataSource: ApiDataSourceImpl): ApiDataSource

    @Binds
    @Singleton
    abstract fun bindApiDataParser(dataParser: ApiDataParserImpl): ApiDataParser

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(dataSource: LocalDataSourceImpl): LocalDataSource
}