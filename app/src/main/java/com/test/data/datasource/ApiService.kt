package com.test.data.datasource

import com.test.data.model.BaseModelMovie
import com.test.data.model.Genres
import com.test.data.model.MovieDetail
import com.test.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): MovieDetail

}