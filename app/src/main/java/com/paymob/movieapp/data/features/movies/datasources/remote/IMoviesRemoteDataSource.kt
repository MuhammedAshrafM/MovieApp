package com.paymob.movieapp.data.features.movies.datasources.remote

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.DataState

interface IMoviesRemoteDataSource {

    suspend fun getMovies(releaseYear: String, sortType: String): DataState<List<Movie>>

}