package com.paymob.movieapp.domain.di

import com.paymob.movieapp.data.features.movies.repository.MoviesRepositoryImpl
import com.paymob.movieapp.domain.usecases.AddMovieToFavoriteUseCase
import com.paymob.movieapp.domain.usecases.GetFavoriteMoviesUseCase
import com.paymob.movieapp.domain.usecases.GetMoviesUseCase
import com.paymob.movieapp.domain.usecases.RemoveMovieFromFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetMoviesUseCase(repository: MoviesRepositoryImpl) =
        GetMoviesUseCase(repository)

    @Singleton
    @Provides
    fun provideGetFavoriteMoviesUseCase(repository: MoviesRepositoryImpl) =
        GetFavoriteMoviesUseCase(repository)


    @Singleton
    @Provides
    fun provideAddMovieToFavoriteUseCase(repository: MoviesRepositoryImpl) =
        AddMovieToFavoriteUseCase(repository)


    @Singleton
    @Provides
    fun provideRemoveMovieFromFavoriteUseCase(repository: MoviesRepositoryImpl) =
        RemoveMovieFromFavoriteUseCase(repository)

}