package com.test.ui.screen.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.test.R
import com.test.data.model.Genre
import com.test.data.model.Genres
import com.test.navigation.Navigation
import com.test.navigation.Screen
import com.test.navigation.currentRoute
import com.test.navigation.navigationTitle
import com.test.ui.component.CircularIndeterminateProgressBar
import com.test.ui.component.SearchBar
import com.test.ui.screen.mainscreen.bottom_navigation.BottomNavigationUI
import com.test.ui.theme.FloatingActionBackground
import com.test.ui.theme.cornerRadius
import com.test.utils.AppConstant
import com.test.utils.network.DataState
import com.test.utils.networkconnection.ConnectionState
import com.test.utils.networkconnection.connectivityState
import com.test.utils.pagingLoadingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    var genreList = remember { mutableListOf<Genre>() }
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isFavoriteActive = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState {
        2
    }

    LaunchedEffect(Unit) {
        mainViewModel.genreList()
    }

    if (mainViewModel.genres.value is DataState.Success<Genres>) {
        genreList =
            (mainViewModel.genres.value as DataState.Success<Genres>).data.genres as ArrayList
        // All first value as all
        if (genreList.first().name != AppConstant.DEFAULT_GENRE_ITEM) genreList.add(
            0, Genre(null, AppConstant.DEFAULT_GENRE_ITEM)
        )
    }

    Scaffold(topBar = {
        if (!isAppBarVisible.value) {
            SearchBar(isAppBarVisible, mainViewModel, pagerState.currentPage)
        } else CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text(
                text = navigationTitle(navController),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }, navigationIcon = {
            when (currentRoute(navController)) {
                Screen.MovieDetail.route, Screen.ArtistDetail.route, Screen.FavoriteMovie.route -> {
                    val activeScreen = currentRoute(navController)
                    IconButton(onClick = {
                        if (isFavoriteActive.value && activeScreen == Screen.FavoriteMovie.route) {
                            val activeMovieTab = Screen.NowPlaying.route
                            navController.navigate(activeMovieTab) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            isFavoriteActive.value = false
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            }
        }, scrollBehavior = scrollBehavior, actions = {
            IconButton(onClick = {
                if (!isFavoriteActive.value) navController.navigate(Screen.FavoriteMovie.route)
                isFavoriteActive.value = true
            }) {
                if (currentRoute(navController) !in listOf(
                        Screen.FavoriteMovie.route,
                        Screen.MovieDetail.route,
                        Screen.ArtistDetail.route
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "favorite",
                        tint = Color.Gray
                    )
                }
            }
        })
    }, floatingActionButton = {
        if (currentRoute(navController) !in listOf(
                Screen.FavoriteMovie.route,
                Screen.MovieDetail.route,
                Screen.ArtistDetail.route
            )
        ) {
            FloatingActionButton(
                modifier = Modifier.cornerRadius(30),
                containerColor = FloatingActionBackground,
                onClick = {
                    isAppBarVisible.value = false
                },
            ) {
                Icon(Icons.Filled.Search, "", tint = Color.White)
            }
        }
    }, bottomBar = {
        when (currentRoute(navController)) {
            Screen.NowPlaying.route, Screen.Popular.route,
            Screen.TopRated.route, Screen.Upcoming.route
                -> {
                BottomNavigationUI(navController, pagerState)
            }
        }
    }, snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(R.string.there_is_no_internet))
            }
        }
    }) {
        Box(Modifier.padding(it)) {
            MainView(
                navController, pagerState, genreList as ArrayList<Genre>?, isFavoriteActive.value
            )
            CircularIndeterminateProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
            if (isAppBarVisible.value.not()) {
                    mainViewModel.movieSearchData.pagingLoadingState {
                        searchProgressBar.value = it
                    }
            }
        }
    }
}

@Composable
fun MainView(
    navigator: NavHostController,
    pagerState: PagerState,
    genres: ArrayList<Genre>? = null,
    isFavorite: Boolean,
) {
    Column {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = false
        ) {
            Navigation(navigator, genres)
        }
    }
}