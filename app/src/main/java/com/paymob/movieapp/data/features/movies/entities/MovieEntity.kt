package com.paymob.movieapp.data.features.movies.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.base.Mapper

@Entity
data class MovieEntity (
    @PrimaryKey
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
    var isBookMarked: Boolean
)

class MoviesLocalMapperToUiModel : Mapper<List<MovieEntity>, List<Movie>>() {
    override fun map(input: List<MovieEntity>): List<Movie> =
        input.map {
            it.asUIModel()
        }
}


fun MovieEntity.asUIModel() = Movie(
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

