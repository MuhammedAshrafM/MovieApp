package com.paymob.movieapp.data.features.movies.models

import android.os.Parcelable
import com.paymob.movieapp.data.features.movies.entities.MovieEntity
import com.paymob.movieapp.data.network.base.Mapper
import kotlinx.android.parcel.Parcelize


@Parcelize
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
    val posterFullPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Int,
    val voteCount: Int,
    val rating: Float,
    var isBookMarked: Boolean = false
): Parcelable


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
    posterFullPath = posterFullPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    rating = rating,
    isBookMarked = isBookMarked
)
