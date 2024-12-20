package com.test.ui.screen.mainscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.model.Genres
import com.test.data.model.SearchBaseModel
import com.test.data.repository.MovieRepository
import com.test.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepo: MovieRepository) : ViewModel() {
    val genres: MutableState<DataState<Genres>?> = mutableStateOf(null)
    val movieSearchData: MutableState<DataState<SearchBaseModel>?> = mutableStateOf(null)
    val tvSeriesSearchData: MutableState<DataState<SearchBaseModel>?> = mutableStateOf(null)

    fun genreList() {
        viewModelScope.launch {
            movieRepo.genreList().onEach {
                genres.value = it
            }.launchIn(viewModelScope)
        }
    }
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchMovie(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    movieRepo.movieSearch(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                    }
                    movieSearchData.value = it
                }
        }
    }
}