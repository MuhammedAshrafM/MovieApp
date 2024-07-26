package com.paymob.movieapp.data.features.movies.datasources.local

import com.paymob.movieapp.data.features.movies.entities.MoviesLocalMapperToModel
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.models.MovieModelMapperToEntity
import com.paymob.movieapp.data.network.base.DataState
import com.paymob.movieapp.data.network.base.getResultDao
import com.paymob.movieapp.data.room.MovieDao
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : IMoviesLocalDataSource {
    override suspend fun getFavoriteMovies(): DataState<List<Movie>> =
        getResultDao(MoviesLocalMapperToModel()) {
            movieDao.getFavoriteMovies()
        }

    override suspend fun addMovieToFavorite(movie: Movie): DataState<Long> =
        getResultDao {
            movieDao.insertMovie(MovieModelMapperToEntity().map(movie))
        }

    override suspend fun removeMovieFromFavorite(movieId: Int): DataState<Int> =
        getResultDao {
            movieDao.removeMovie(
                movieId = movieId
            )
        }
}