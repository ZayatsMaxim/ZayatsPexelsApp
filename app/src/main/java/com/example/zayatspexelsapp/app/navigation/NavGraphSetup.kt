package com.example.zayatspexelsapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zayatspexelsapp.app.ui.presentation.bookmarks.BookmarksPage
import com.example.zayatspexelsapp.app.ui.presentation.details.DetailsPage
import com.example.zayatspexelsapp.app.ui.presentation.home.HomePage
import com.example.zayatspexelsapp.app.ui.presentation.splash.AnimatedSplashScreen

@Composable
fun NavGraphSetup(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) { AnimatedSplashScreen(navController) }
        composable(Screen.Home.route) { HomePage(navController) }
        composable(Screen.Bookmarks.route) { BookmarksPage(navController) }
        composable(Screen.Details.route) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailsPage(navController = navController, id = arguments.getString("id"))
        }
    }
}