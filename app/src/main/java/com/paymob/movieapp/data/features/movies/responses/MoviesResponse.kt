package com.paymob.movieapp.data.features.movies.responses

import com.google.gson.annotations.SerializedName
import com.paymob.movieapp.BuildConfig
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.Mapper

data class MoviesResponse(
    val page: Int?,
    val results: List<MovieResponse>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)
data class MovieResponse (
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    val genreIDS: List<Int>?,

    val id: Int?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    val overview: String?,
    val popularity: Float?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    val title: String?,
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Int?,

    @SerializedName("vote_count")
    val voteCount: Int?
)


class MoviesResponseMapperToUiModel : Mapper<MoviesResponse, List<Movie>>() {
    override fun map(input: MoviesResponse): List<Movie> =
        input.results?.map {
            it.asUIModel()
        } ?: listOf()
}


fun MovieResponse.asUIModel() = Movie(
    id = id ?: 0,
    adult = adult ?: false,
    backdropPath = backdropPath ?: "",
    genreIDS = genreIDS ?: listOf(),
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0f,
    posterPath = posterPath ?: "",
    posterFullPath = "${BuildConfig.IMAGE_URL}${posterPath ?: ""}",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0,
    voteCount = voteCount ?: 0,
    rating = ((voteAverage?.toFloat() ?: 0f) / 10f) * 5
)
