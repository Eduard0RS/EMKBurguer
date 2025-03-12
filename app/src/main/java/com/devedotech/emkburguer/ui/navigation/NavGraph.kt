package com.devedotech.emkburguer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devedotech.emkburguer.ui.auth.AuthScreen
import com.devedotech.emkburguer.ui.auth.AuthViewModel
import com.devedotech.emkburguer.ui.home.HomeScreen
import com.devedotech.emkburguer.ui.home.HomeViewModel
import androidx.navigation.compose.composable


sealed class Screen(val route: String) {
    data object Auth : Screen("auth_screen")
    data object SignUp : Screen("signup_screen")
    data object Home : Screen("home_screen")
    data object ForgotPassword : Screen("forgot_password_screen")
}

@Composable
fun NavGraph(startDestination: String = Screen.Auth.route) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        // Tela de login (AuthScreen)
        composable(Screen.Auth.route) {
            AuthScreen(navController, AuthViewModel(LocalContext.current))
        }

//        // Tela de cadastro (SignUpScreen)
//        composable(Screen.SignUp.route) {
//            SignUpScreen(navController)
//        }
//
        // Tela principal (HomeScreen)
        composable(Screen.Home.route) {
            HomeScreen(navController, HomeViewModel(LocalContext.current))
        }
    }
}
