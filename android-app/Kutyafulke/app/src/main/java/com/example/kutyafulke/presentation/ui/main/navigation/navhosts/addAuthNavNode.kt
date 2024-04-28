package com.example.kutyafulke.presentation.ui.main.navigation.navhosts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.kutyafulke.presentation.ui.login.LoginScreen
import com.example.kutyafulke.presentation.ui.main.navigation.routes.AuthScreenRoute
import com.example.kutyafulke.presentation.ui.main.navigation.routes.NavNodeRoute
import com.example.kutyafulke.presentation.ui.register.RegisterScreen
import com.example.kutyafulke.presentation.ui.resetpassword.ResetPasswordScreen

fun NavGraphBuilder.addAuthNavNode(navHostController: NavHostController) {
    navigation(
        route = NavNodeRoute.AUTH,
        startDestination = AuthScreenRoute.LOGIN
    ) {
        composable(route = AuthScreenRoute.LOGIN) {
            LoginScreen(
                navigateToResetPasswordScreen = {
                    navHostController.navigate(AuthScreenRoute.RESET_PASSWORD)
                },
                navigateToRegisterScreen = {
                    navHostController.navigate(AuthScreenRoute.REGISTER)
                },
                navigateToMainScreens = {
                    navHostController.navigate(NavNodeRoute.MAIN)
                }
            )
        }
        composable(route = AuthScreenRoute.REGISTER) {
            RegisterScreen(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
        composable(route = AuthScreenRoute.RESET_PASSWORD) {
            ResetPasswordScreen(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}