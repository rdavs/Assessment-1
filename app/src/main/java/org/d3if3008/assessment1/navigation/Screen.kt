package org.d3if3008.assessment1.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
    data object Circle: Screen("circleScreen")
    data object Rectangle: Screen("rectangleScreen")
    data object Square: Screen("squareScreen")
    data object Triangle: Screen("triangleScreen")
}