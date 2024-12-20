package com.test.data.repository

import android.media.tv.TvContract
import androidx.paging.PagingData
import com.test.data.model.Artist
import com.test.data.model.ArtistDetail
import com.test.data.model.Genres
import com.test.data.model.MovieDetail
import com.test.data.model.MovieItem
import com.test.data.model.SearchBaseModel
import com.test.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    suspend fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>>
    suspend fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>>
    suspend fun genreList(): Flow<DataState<Genres>>
    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>>
    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>
    fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>
}