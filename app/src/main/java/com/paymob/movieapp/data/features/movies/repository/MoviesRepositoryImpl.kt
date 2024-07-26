package com.paymob.movieapp.data.features.movies.repository

import com.paymob.movieapp.data.features.movies.datasources.local.IMoviesLocalDataSource
import com.paymob.movieapp.data.features.movies.datasources.remote.IMoviesRemoteDataSource
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.DataState
import com.paymob.movieapp.data.network.base.flowStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: IMoviesRemoteDataSource,
    private val moviesLocalDataSource: IMoviesLocalDataSource
) : IMoviesRepository {


    override suspend fun getMovies(
        releaseYear: String,
        sortType: String
    ): Flow<DataState<List<Movie>>> =
        flowStatus {
            moviesRemoteDataSource.getMovies(
                releaseYear = releaseYear,
                sortType = sortType
            )
        }

    override suspend fun getFavoriteMovies(): Flow<DataState<List<Movie>>> =
        flowStatus {
            moviesLocalDataSource.getFavoriteMovies()
        }

    override suspend fun addMovieToFavorite(movie: Movie): Flow<DataState<Long>> =
        flowStatus {
            moviesLocalDataSource.addMovieToFavorite(
                movie = movie
            )
        }

    override suspend fun removeMovieFromFavorite(movieId: Int): Flow<DataState<Int>> =
        flowStatus {
            moviesLocalDataSource.removeMovieFromFavorite(
                movieId = movieId
            )
        }

}