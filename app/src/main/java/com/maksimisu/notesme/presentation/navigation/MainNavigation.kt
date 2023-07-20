package com.maksimisu.notesme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maksimisu.notesme.presentation.screens.config.ConfigScreen
import com.maksimisu.notesme.presentation.screens.edit.EditScreen
import com.maksimisu.notesme.presentation.screens.home.HomeScreen
import com.maksimisu.notesme.presentation.screens.read.ReadScreen

sealed class MainNavigation(val route: String) {
    object HomeScreen : MainNavigation("screen_home")
    object ReadScreen : MainNavigation("screen_read")
    object EditScreen : MainNavigation("screen_edit")
    object ConfigScreen : MainNavigation("screen_config")
}

@Composable
fun SetUpNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = MainNavigation.HomeScreen.route
    ) {

        // HOME
        composable(route = MainNavigation.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }

        // READ
        composable(
            route = MainNavigation.ReadScreen.route + "/{name}",
            arguments = listOf(
                navArgument(name = "name") {
                    nullable = false
                    type = NavType.StringType
                }
            )
        ) {
            ReadScreen(
                navHostController = navHostController,
                name = it.arguments!!.getString("name", "README")
            )
        }

        // EDIT
        composable(
            route = MainNavigation.EditScreen.route + "?name={name}",
            arguments = listOf(
                navArgument(name = "name") {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) {
            EditScreen(
                navHostController = navHostController,
                name = it.arguments?.getString("name")
            )
        }

        // CONFIG
        composable(route = MainNavigation.ConfigScreen.route) {
            ConfigScreen(navHostController = navHostController)
        }

    }
}
