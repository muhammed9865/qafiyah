package com.salman.qafiyah.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.salman.qafiyah.presentation.navigation.NavigationGraph
import com.salman.qafiyah.presentation.screen.home.HomeScreen
import com.salman.qafiyah.presentation.screen.settings.SettingsScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 */
object MainGraph : NavigationGraph(startDestination = Routes.home, route = "main") {

    object Routes {
        const val home = "home"
        const val settings = "settings"
    }

    override fun navigation(navController: NavController, navGraphBuilder: NavGraphBuilder) =
        with(navGraphBuilder) {
            navigation(
                startDestination = this@MainGraph.startDestination,
                route = this@MainGraph.route,
            ) {
                composable(Routes.home) {
                    HomeScreen()
                }
                composable(Routes.settings) {
                    SettingsScreen()
                }

            }
        }
}