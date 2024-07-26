package com.paymob.movieapp.data.features.movies.repository

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.DataState
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

     suspend fun getMovies(releaseYear: String, sortType: String): Flow<DataState<List<Movie>>>

     suspend fun getFavoriteMovies(): Flow<DataState<List<Movie>>>

     suspend fun addMovieToFavorite(movie: Movie): Flow<DataState<Long>>

     suspend fun removeMovieFromFavorite(movieId: Int): Flow<DataState<Int>>
}