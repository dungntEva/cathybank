package com.test.data.datasource

import com.test.data.model.Artist
import com.test.data.model.ArtistDetail
import com.test.data.model.BaseModelMovie
import com.test.data.model.Genres
import com.test.data.model.MovieDetail
import com.test.data.model.SearchBaseModel
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

    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("movie/top_rated")
    suspend fun topRatedMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("movie/upcoming")
    suspend fun upcomingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): MovieDetail

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("search/movie?page=1&include_adult=false")
    suspend fun searchMovie(
        @Query("query") searchKey: String, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): SearchBaseModel

    @GET("genre/movie/list")
    suspend fun genreList(@Query("api_key") apiKey: String = AppConstant.API_KEY): Genres

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): BaseModelMovie

    @GET("movie/{movieId}/credits")
    suspend fun movieCredit(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): Artist

    @GET("person/{personId}")
    suspend fun artistDetail(
        @Path("personId") personId: Int, @Query("api_key") apiKey: String = AppConstant.API_KEY,
    ): ArtistDetail
}