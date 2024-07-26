package com.paymob.movieapp.data.features.movies.datasources.remote

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.responses.MoviesResponseMapperToModel
import com.paymob.movieapp.data.network.apis.MovieAPI
import com.paymob.movieapp.data.network.base.DataState
import com.paymob.movieapp.data.network.base.getResultRestAPI
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
    private val apiService: MovieAPI
) : IMoviesRemoteDataSource {
    override suspend fun getMovies(releaseYear: String, sortType: String): DataState<List<Movie>> =
        getResultRestAPI(MoviesResponseMapperToModel()) {
            apiService.getMovies(
                releaseYear = releaseYear,
                sortType = sortType
            )
        }
}