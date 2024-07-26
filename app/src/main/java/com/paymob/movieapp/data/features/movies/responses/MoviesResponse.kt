package com.paymob.movieapp.data.features.movies.responses

import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.Mapper
import kotlinx.serialization.SerialName

data class MoviesResponse(
    val page: Int?,
    val results: List<MovieResponse>?,

    @SerialName("total_pages")
    val totalPages: Int?,

    @SerialName("total_results")
    val totalResults: Int?
)
data class MovieResponse (
    val adult: Boolean?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("genre_ids")
    val genreIDS: List<Int>?,

    val id: Int?,

    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("original_title")
    val originalTitle: String?,

    val overview: String?,
    val popularity: Float?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    val title: String?,
    val video: Boolean?,

    @SerialName("vote_average")
    val voteAverage: Int?,

    @SerialName("vote_count")
    val voteCount: Int?
)


class MoviesResponseMapperToModel : Mapper<MoviesResponse, List<Movie>>() {
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
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0,
    voteCount = voteCount ?: 0,
)
