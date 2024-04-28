package com.example.kutyafulke.presentation.ui.main.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kutyafulke.presentation.ui.breeds.BreedsScreen
import com.example.kutyafulke.presentation.ui.home.HomeScreen
import com.example.kutyafulke.presentation.ui.main.navigation.routes.NavNodeRoute
import com.example.kutyafulke.presentation.ui.main.navigation.routes.MainScreenRoute

@Composable
fun MainNavSubTree(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = NavNodeRoute.MAIN,
        startDestination = MainScreenRoute.HOME,
        builder = {
            composable(route = MainScreenRoute.HOME) {
                HomeScreen()
            }
            composable(route = MainScreenRoute.BREEDS) {
                BreedsScreen()
            }
        }
    )
}