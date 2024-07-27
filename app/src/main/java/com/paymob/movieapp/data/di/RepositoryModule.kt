package com.paymob.movieapp.data.di

import com.paymob.movieapp.data.features.movies.datasources.local.IMoviesLocalDataSource
import com.paymob.movieapp.data.features.movies.datasources.local.MoviesLocalDataSourceImpl
import com.paymob.movieapp.data.features.movies.datasources.remote.IMoviesRemoteDataSource
import com.paymob.movieapp.data.features.movies.datasources.remote.MoviesRemoteDataSourceImpl
import com.paymob.movieapp.data.features.movies.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        moviesRemoteDataSource: MoviesRemoteDataSourceImpl,
        moviesLocalDataSource: MoviesLocalDataSourceImpl
    ) = MoviesRepositoryImpl(
        moviesRemoteDataSource = moviesRemoteDataSource,
        moviesLocalDataSource = moviesLocalDataSource
    )

}