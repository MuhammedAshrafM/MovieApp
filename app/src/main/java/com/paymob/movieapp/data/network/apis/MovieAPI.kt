package com.paymob.movieapp.data.network.apis

import com.paymob.movieapp.data.features.movies.responses.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie")
    suspend fun getMovies(
        @Query("primary_release_year") releaseYear: String,
        @Query("sort_by") sortType: String
    ): Response<MoviesResponse>

}