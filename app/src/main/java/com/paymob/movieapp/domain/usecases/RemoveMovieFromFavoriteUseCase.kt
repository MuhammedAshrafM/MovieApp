package com.paymob.movieapp.domain.usecases

import com.paymob.movieapp.data.features.movies.repository.IMoviesRepository
import com.paymob.movieapp.data.network.base.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveMovieFromFavoriteUseCase @Inject constructor(private val repository: IMoviesRepository) {
    suspend operator fun invoke(movieId: Int): Flow<DataState<Int>> =
        repository.removeMovieFromFavorite(
            movieId = movieId
        )
}