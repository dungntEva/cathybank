package com.test.ui.screen.movies.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.datasource.dao.FavoriteMovieDao
import com.test.data.model.Artist
import com.test.data.model.MovieDetail
import com.test.data.model.MovieItem
import com.test.data.repository.MovieRepository
import com.test.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repo: MovieRepository,
    private val movieDetailDao: FavoriteMovieDao,
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _recommendedMovie = MutableStateFlow<List<MovieItem>>(arrayListOf())
    val recommendedMovie get() = _recommendedMovie.asStateFlow()

    private val _movieCredit = MutableStateFlow<Artist?>(null)
    val movieCredit get() = _movieCredit.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun movieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.movieDetail(movieId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _movieDetail.value = it.data
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun insertMovieDetail(movieDetail: MovieDetail) {
        viewModelScope.launch {
            movieDetailDao.insert(movieDetail)
        }
    }

    suspend fun getMovieDetailById(id: Int): MovieDetail? {
        return movieDetailDao.getMovieDetailById(id)
    }

    fun deleteMovieDetailById(id: Int) {
        viewModelScope.launch {
            movieDetailDao.deleteMovieDetailById(id)
        }
    }
}