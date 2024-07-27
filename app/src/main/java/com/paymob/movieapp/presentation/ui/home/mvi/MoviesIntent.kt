package com.paymob.movieapp.presentation.ui.home.mvi

import com.paymob.movieapp.data.features.movies.models.Movie

sealed interface MoviesIntent{
    object UserMessageShown : MoviesIntent
    object GetMovies : MoviesIntent
    data class ChangeReleaseYear(val releaseYear: String?) : MoviesIntent
    object ChangeSortType : MoviesIntent
    object ChangeListViewType : MoviesIntent
    object GetFavouriteMovies : MoviesIntent
    data class ChangeMovieFavorite(val movie: Movie) : MoviesIntent

}
