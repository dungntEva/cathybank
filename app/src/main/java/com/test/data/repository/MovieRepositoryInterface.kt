package com.test.data.repository

import android.media.tv.TvContract
import androidx.paging.PagingData
import com.test.data.model.Artist
import com.test.data.model.ArtistDetail
import com.test.data.model.Genres
import com.test.data.model.MovieDetail
import com.test.data.model.MovieItem
import com.test.data.model.SearchBaseModel
import com.test.data.model.TravelItem
import com.test.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun travelPagingDataSource(lang: String?): Flow<PagingData<TravelItem>>
}