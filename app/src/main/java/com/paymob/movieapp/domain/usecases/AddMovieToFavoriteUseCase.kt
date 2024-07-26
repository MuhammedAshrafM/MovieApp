package com.paymob.movieapp.domain.usecases

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.repository.IMoviesRepository
import com.paymob.movieapp.data.network.base.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(private val repository: IMoviesRepository) {
    suspend operator fun invoke(movie: Movie): Flow<DataState<Long>> =
        repository.addMovieToFavorite(
            movie = movie
        )
}