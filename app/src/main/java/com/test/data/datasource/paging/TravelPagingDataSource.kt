package com.test.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.data.datasource.ApiService
import com.test.data.model.TravelItem
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class TravelPagingDataSource @Inject constructor(private val apiService: ApiService, private val lang:String?) :
    PagingSource<Int, TravelItem>() {

    override fun getRefreshKey(state: PagingState<Int, TravelItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TravelItem> {
        return try {
            val nextPage = params.key ?: 1
            val travelList = apiService.getAllTravel(lang ?: "en", nextPage)
            LoadResult.Page(
                data = travelList.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =  if (travelList.data.isNotEmpty()) travelList.total + 1 else  null
            )
        } catch (exception: IOException) {
            Timber.e("exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Timber.e("httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }
}