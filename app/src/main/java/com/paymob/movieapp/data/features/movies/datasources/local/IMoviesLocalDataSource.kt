package com.paymob.movieapp.data.features.movies.datasources.local

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.DataState

interface IMoviesLocalDataSource {
    suspend fun getFavoriteMovies(): DataState<List<Movie>>

    suspend fun addMovieToFavorite(movie: Movie): DataState<Long>

    suspend fun removeMovieFromFavorite(movieId: Int): DataState<Int>
}