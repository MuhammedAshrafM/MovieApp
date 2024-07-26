package com.paymob.movieapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymob.movieapp.data.features.movies.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(IntConverter::class)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun eduTracDao(): MovieDao

}