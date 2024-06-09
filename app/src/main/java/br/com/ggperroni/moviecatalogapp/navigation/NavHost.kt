package br.com.ggperroni.moviecatalogapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "BannerScreen") {
        composable("BannerScreen") {
            BannerScreen(navController = navController)
        }
        composable("HomeScreen") {
            HomeScreen(navController = navController)
        }
        composable("DetailScreen/{id}", arguments = listOf(navArgument(name = "id") {
            type = NavType.IntType
        })) {
            it.arguments?.getInt("id")?.let { id ->
                DetailScreen(id = id)
            }
        }
    }
}
