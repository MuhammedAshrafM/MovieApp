package com.paymob.movieapp.presentation.ui.home.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymob.movieapp.data.features.movies.models.ListViewType
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.features.movies.models.SortType
import com.paymob.movieapp.domain.usecases.AddMovieToFavoriteUseCase
import com.paymob.movieapp.domain.usecases.GetFavoriteMoviesUseCase
import com.paymob.movieapp.domain.usecases.GetMoviesUseCase
import com.paymob.movieapp.domain.usecases.RemoveMovieFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase,
) : ViewModel() {

    private var viewModelJob: Job = Job()

    private val coroutinesScope: CoroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val _viewIntent = Channel<MoviesIntent>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow(MoviesUiState())
    val viewState = _viewState.asSharedFlow()

    init {
        processIntent()
    }

    fun setViewIntent(intent: MoviesIntent) = viewModelScope.launch {
        _viewIntent.send(intent)
    }

    private fun processIntent() {
        viewModelScope.launch {
            _viewIntent.consumeAsFlow().collect {
                when (it) {
                    is MoviesIntent.UserMessageShown -> userMessageShown()
                    is MoviesIntent.GetMovies -> getMovies()
                    is MoviesIntent.ChangeReleaseYear -> changeReleaseYear(it.releaseYear)
                    is MoviesIntent.ChangeSortType -> changeSortType()
                    is MoviesIntent.ChangeListViewType -> changeListViewType()
                    is MoviesIntent.GetFavouriteMovies -> getFavouriteMovies()
                    is MoviesIntent.ChangeMovieFavorite -> changeMovieFavorite(it.movie)
                }
            }
        }
    }

    private fun loadMovies(releaseYear: String, sortType: SortType){
        coroutinesScope.launch {
            getMoviesUseCase(
                releaseYear = releaseYear,
                sortType = sortType.value
            ).collect { response ->
                if (response.data?.isNotEmpty() == true){
                    getFavoriteMoviesUseCase().collect { responseLocal ->
                        responseLocal.data?.let {
                            injectBookmarkedMoviesToMovies(response.data, responseLocal.data)
                        }
                    }
                }
                _viewState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = response.loading,
                        error = response.errorEntity,
                        movies = response.data ?: listOf(),
                        releaseYear = releaseYear,
                        sortType = sortType
                    )
                }
            }
        }
    }

    private fun injectBookmarkedMoviesToMovies(movies: List<Movie>, bookmarkedMovies: List<Movie>) {
        coroutinesScope.launch {
            bookmarkedMovies.sortedBy { it.id }.onEach { bookmarkedMovie ->
                movies.sortedBy { it.id }.first { movie ->
                    movie.id == bookmarkedMovie.id
                }.apply {
                    isBookMarked = true
                }
            }

            if (movies.isNotEmpty())
                _viewState.update { currentUiState ->
                    currentUiState.copy(
                        movies = movies
                    )
                }
        }
    }

    private fun getMovies() {
        val (releaseYear, sortType) = _viewState.value.run {
            releaseYear to sortType
        }

        loadMovies(
            releaseYear = releaseYear,
            sortType = sortType
        )
    }

    private fun changeReleaseYear(releaseYear: String) {
        val sortType = _viewState.value.sortType

        loadMovies(
            releaseYear = releaseYear,
            sortType = sortType
        )
    }

    private fun changeSortType() {
        val (releaseYear, sortType) = _viewState.value.run {
            releaseYear to if (sortType == SortType.DESC) SortType.ASC else SortType.DESC
        }

        loadMovies(
            releaseYear = releaseYear,
            sortType = sortType
        )
    }

    private fun changeListViewType(){
        val listViewType = _viewState.value.run {
            if (listViewType == ListViewType.LINEAR) ListViewType.GRID else ListViewType.LINEAR
        }

        _viewState.update { currentUiState ->
            currentUiState.copy(
                listViewType = listViewType
            )
        }
    }

    private fun getFavouriteMovies() {
        coroutinesScope.launch {
            getFavoriteMoviesUseCase().collect { response ->
                _viewState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = response.loading,
                        error = response.errorEntity,
                        movies = response.data ?: listOf()
                    )
                }
            }
        }
    }

    private fun changeMovieFavorite(movie: Movie){
        if (movie.isBookMarked) {
            movie.apply {
                isBookMarked = false
            }
            removeMovieFromFavorite(movie.id)
        }
        else {
            addMovieToFavorite(
                movie.apply{
                    isBookMarked = true
                }
            )
        }
    }

    private fun addMovieToFavorite(movie: Movie) {
        coroutinesScope.launch {
            addMovieToFavoriteUseCase(
                movie = movie
            ).collect { response ->
                _viewState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = response.loading,
                        error = response.errorEntity,
//                        movieAddedToFavourite = if (response.data != null) movie.id else null
                    )
                }
//                _viewState.update { currentUiState ->
//                    currentUiState.copy(
//                        movieAddedToFavourite = null
//                    )
//                }
            }
        }
    }

    private fun removeMovieFromFavorite(movieId: Int) {
        coroutinesScope.launch {
            removeMovieFromFavoriteUseCase(
                movieId = movieId
            ).collect { response ->
                _viewState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = response.loading,
                        error = response.errorEntity,
//                        movieRemovedFromFavourite = if (response.data != null) movieId else null
                    )
                }
//                _viewState.update { currentUiState ->
//                    currentUiState.copy(
//                        movieRemovedFromFavourite = null
//                    )
//                }
            }
        }
    }

    private fun userMessageShown() {
        _viewState.update { currentUiState ->
            currentUiState.copy(
                error = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}