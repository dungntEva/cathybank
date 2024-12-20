package com.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.data.datasource.ApiService
import com.test.data.datasource.paging.NowPlayingMoviePagingDataSource
import com.test.data.model.Artist
import com.test.data.model.ArtistDetail
import com.test.data.model.Genres
import com.test.data.model.MovieDetail
import com.test.data.model.MovieItem
import com.test.data.model.SearchBaseModel
import com.test.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) : MovieRepositoryInterface {
    override suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { NowPlayingMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

}