package com.test.ui.screen.nowplaying

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.data.model.GenreId
import com.test.data.model.Genre
import com.test.ui.component.MovieItems

@Composable
fun NowPlayingMovie(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val nowPlayViewModel = hiltViewModel<NowPlayingMovieViewModel>()
    MovieItems(
        navController = navController,
        moviesItems = nowPlayViewModel.nowPlayingMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = nowPlayViewModel.selectedGenre.value
    ){
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}