package com.paymob.movieapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paymob.movieapp.data.features.movies.entities.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getFavoriteMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Query("DELETE FROM MovieEntity WHERE id = :movieId")
    suspend fun removeMovie(movieId: Int): Int

}