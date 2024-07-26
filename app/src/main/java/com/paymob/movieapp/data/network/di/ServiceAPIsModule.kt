package com.paymob.movieapp.data.network.di

import com.paymob.movieapp.data.network.apis.MovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceAPIsModule {

    @Singleton
    @Provides
    fun provideMovieAPIService(retrofit: Retrofit.Builder): MovieAPI =
        retrofit.build().create(MovieAPI::class.java)

}