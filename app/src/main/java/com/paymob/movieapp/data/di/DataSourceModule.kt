package com.paymob.movieapp.data.di

import com.paymob.movieapp.data.features.movies.datasources.local.MoviesLocalDataSourceImpl
import com.paymob.movieapp.data.features.movies.datasources.remote.MoviesRemoteDataSourceImpl
import com.paymob.movieapp.data.network.apis.MovieAPI
import com.paymob.movieapp.data.room.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(
        apiService: MovieAPI
    ) = MoviesRemoteDataSourceImpl(
        apiService = apiService
    )

    @Singleton
    @Provides
    fun provideMoviesLocalDataSource(
        movieDao: MovieDao
    ) = MoviesLocalDataSourceImpl(
        movieDao = movieDao
    )

}