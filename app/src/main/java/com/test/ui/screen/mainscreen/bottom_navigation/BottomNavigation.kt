package com.test.ui.screen.mainscreen.bottom_navigation

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.test.navigation.Screen
import com.test.navigation.currentRoute
import com.test.utils.singleTopNavigator

@Composable
fun BottomNavigationUI(navController: NavController, pagerState: PagerState) {
    NavigationBar {
        val items =
            listOf(
                Screen.NowPlayingNav,
                Screen.PopularNav,
                Screen.TopRatedNav,
                Screen.UpcomingNav,
            )
        items.forEachIndexed { index, item ->
            NavigationBarItem(icon = item.navIcon,
                label = { Text(text = stringResource(id = item.title)) },
                selected = currentRoute(navController) == item.route,
                onClick = {
                    navController.singleTopNavigator(item.route)
                })
        }
    }
}