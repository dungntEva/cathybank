package com.test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.test.R
import com.test.data.model.Genre
import com.test.ui.screen.artist_detail.ArtistDetail
import com.test.ui.screen.movies.movie_detail.MovieDetail
import com.test.ui.screen.nowplaying.NowPlayingMovie
import com.test.ui.screen.popular.PopularMovie
import com.test.ui.screen.toprated.TopRatedMovie
import com.test.ui.screen.upcoming.UpcomingMovie

@Composable
fun Navigation(
    navController: NavHostController, genres: ArrayList<Genre>? = null,
) {
    NavHost(navController, startDestination =  Screen.NowPlaying.route) {
        composable(Screen.NowPlaying.route) {
            NowPlayingMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.Popular.route) {
            PopularMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.TopRated.route) {
            TopRatedMovie(
                navController = navController,
                genres
            )
        }
        composable(Screen.Upcoming.route) {
            UpcomingMovie(
                navController = navController,
                genres
            )
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.movie_detail)
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            movieId?.let {
                MovieDetail(
                    navController = navController, movieId
                )
            }
        }
        composable(
            Screen.ArtistDetail.route.plus(Screen.ArtistDetail.objectPath),
            arguments = listOf(navArgument(Screen.ArtistDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            label = stringResource(R.string.artist_detail)
            val artistId = it.arguments?.getInt(Screen.ArtistDetail.objectName)
            artistId?.let {
                ArtistDetail(
                    artistId
                )
            }
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.MovieDetail.route -> stringResource(id = R.string.movie_detail)
        Screen.ArtistDetail.route -> stringResource(id = R.string.artist_detail)
        Screen.FavoriteMovie.route -> stringResource(id = R.string.favorite_movie)
        else -> {
            stringResource(R.string.app_name)
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}