package com.test.ui.screen.popular

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.data.model.Genre
import com.test.data.model.GenreId
import com.test.ui.component.MovieItems

@Composable
fun PopularMovie(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val popularViewModel = hiltViewModel<PopularMovieViewModel>()
    MovieItems(
        navController = navController,
        moviesItems = popularViewModel.popularMovies.collectAsLazyPagingItems(),
        genres = genres,
        selectedName = popularViewModel.selectedGenre.value
    ){
        popularViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            popularViewModel.selectedGenre.value = it
        }
    }
}