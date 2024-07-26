package com.paymob.movieapp.domain.usecases

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.repository.IMoviesRepository
import com.paymob.movieapp.data.network.base.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: IMoviesRepository) {
    suspend operator fun invoke(releaseYear: String, sortType: String): Flow<DataState<List<Movie>>> =
        repository.getMovies(
            releaseYear = releaseYear,
            sortType = sortType
        )
}