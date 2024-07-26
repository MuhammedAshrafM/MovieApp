package com.paymob.movieapp.data.features.movies.models

import com.paymob.movieapp.data.features.movies.entities.MovieEntity
import com.paymob.movieapp.data.network.base.Mapper


data class Movie (
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIDS: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Int,
    val voteCount: Int
)


class MovieModelMapperToEntity : Mapper<Movie, MovieEntity>() {
    override fun map(input: Movie): MovieEntity =
        input.asUIModel()
}


fun Movie.asUIModel() = MovieEntity(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    genreIDS = genreIDS,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)
