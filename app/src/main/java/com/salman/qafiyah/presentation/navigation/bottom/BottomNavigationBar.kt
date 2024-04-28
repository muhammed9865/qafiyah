package com.salman.qafiyah.presentation.navigation.bottom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.salman.qafiyah.R
import com.salman.qafiyah.presentation.navigation.LocalNavigator
import com.salman.qafiyah.presentation.navigation.graphs.MainGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
@Composable
fun BottomNavigationBar() {
    // Declaration is done here since it's used only for this function
    data class NavigationData(
        val title: String,
        @DrawableRes val icon: Int,
        val route: String,
    )

    val items = listOf(
        NavigationData(title = stringResource(R.string.home), R.drawable.ic_home, MainGraph.Routes.home),
        NavigationData(title = stringResource(R.string.settings), R.drawable.ic_settings, MainGraph.Routes.settings),
    )

    val navigator = LocalNavigator.current
    val backStack by navigator.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    NavigationBar(
    ) {
        items.forEach {
            BottomNavigationItem(
                title = it.title,
                icon = it.icon,
                isSelected = currentRoute == it.route,
                onClick = {
                    navigator.navigate(it.route) {
                        popUpTo(navigator.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    title: String,
    icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected, onClick = onClick,
        icon = {
            Icon(painter = painterResource(id = icon), contentDescription = title)
        },
        label = { Text(text = title) },
        colors = NavigationBarItemDefaults.colors(
        )
    )
}