package com.example.noticeapp2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noticeapp2.presentation.home_screen.HomeScreen
import com.example.noticeapp2.presentation.signin_screen.SignInScreen
import com.example.noticeapp2.presentation.signup_screen.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.SignUpScreen.route) {
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}