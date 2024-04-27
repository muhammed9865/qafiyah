package com.salman.qafiyah.presentation.navigation

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.salman.qafiyah.presentation.navigation.bottom.BottomNavigationBar
import com.salman.qafiyah.presentation.navigation.graphs.MainGraph

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/29/2024.
 *
 * @param intent The intent that is used to navigate to a specific screen (deep link)
 */
@Composable
fun AppNavigation(intent: Intent? = null) {
    val navController = rememberNavController()
    val graphs = listOf(
        MainGraph,
    )
    LaunchedEffect(key1 = intent?.data) {
        if (intent?.data != null) {
            navController.navigate(intent.data.toString())
        }
    }
    CompositionLocalProvider(LocalNavigator provides navController) {
        Scaffold(
            bottomBar = { BottomNavigationBar() }
        ) { contentPadding ->
            NavHost(
                modifier = Modifier.padding(contentPadding),
                navController = navController,
                startDestination = graphs.first().route,
            ) {
                graphs.forEach { it.navigation(navController, this) }
            }
        }
    }
}

val LocalNavigator = staticCompositionLocalOf<NavHostController> {
    error("LocalNavigator not initialized")
}