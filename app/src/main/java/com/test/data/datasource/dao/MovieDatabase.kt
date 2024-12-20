package com.test.data.datasource.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.data.model.MovieDetail

@TypeConverters(MovieTypeConverter::class)
@Database(version = 1, entities = [MovieDetail::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDetailDao(): FavoriteMovieDao
}