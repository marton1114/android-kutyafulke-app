package com.example.kutyafulke.presentation.ui.main.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kutyafulke.presentation.ui.main.MainScreen
import com.example.kutyafulke.presentation.ui.main.navigation.routes.NavNodeRoute

@Composable
fun RootNavNode(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = NavNodeRoute.ROOT,
        startDestination = NavNodeRoute.AUTH,
        builder = {
            addAuthNavNode(navHostController = navHostController)

            composable(route = NavNodeRoute.MAIN) {
                MainScreen(navHostController = navHostController)
            }
        }
    )
}