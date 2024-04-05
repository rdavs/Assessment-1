package org.d3if3008.assessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.d3if3008.assessment1.ui.screen.AboutScreen
import org.d3if3008.assessment1.ui.screen.CircleScreen
import org.d3if3008.assessment1.ui.screen.MainScreen
import org.d3if3008.assessment1.ui.screen.RectangleScreen
import org.d3if3008.assessment1.ui.screen.SquareScreen
import org.d3if3008.assessment1.ui.screen.TriangleScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Circle.route) {
            CircleScreen(navController)
        }
        composable(route = Screen.Square.route) {
            SquareScreen(navController)
        }
        composable(route = Screen.Rectangle.route) {
            RectangleScreen(navController)
        }
        composable(route = Screen.Triangle.route) {
            TriangleScreen(navController)
        }

    }
}