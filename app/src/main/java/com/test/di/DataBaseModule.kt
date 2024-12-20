package com.test.di

import android.content.Context
import androidx.room.Room
import com.test.data.datasource.dao.FavoriteMovieDao
import com.test.data.datasource.dao.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movieWorld.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDetailDao(moviesDatabase: MovieDatabase): FavoriteMovieDao =
        moviesDatabase.getFavoriteMovieDetailDao()
}