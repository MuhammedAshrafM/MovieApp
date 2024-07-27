package com.paymob.movieapp.presentation.ui.home.mvi

import com.paymob.movieapp.data.features.movies.models.ListViewType
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.models.SortType
import com.paymob.movieapp.data.network.error.ErrorEntity


data class MoviesUiState(
    val isLoading : Boolean = false,
    val error: ErrorEntity? = null,
    val movies: List<Movie> = listOf(),
    val sortType: SortType = SortType.DESC,
    val releaseYear: String = "2024",
    val listViewType: ListViewType = ListViewType.LINEAR
)
