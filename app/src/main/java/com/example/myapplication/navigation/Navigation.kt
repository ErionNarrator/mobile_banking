package com.example.myapplication.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.view.Greeting
import com.example.myapplication.view.GreetingB
import com.example.myapplication.view.HomeScreen
import com.example.myapplication.view.LoginScreen
import com.example.myapplication.view.RegisterScreen
import com.example.myapplication.view.TransferScreen
import com.example.myapplication.viewmodel.AuthViewModel


@Composable
fun Navigate(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        composable("Greeting") { Greeting(navController) }
        composable("GreetingB") { GreetingB(navController) }
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToRegister = { navController.navigate("register") },
                authViewModel = authViewModel
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home") },
                onNavigateToLogin = { navController.navigate("login") },
//                authViewModel = authViewModel
            )
        }

        composable("home") {
            HomeScreen(
                onNavigateToTransfer = { navController.navigate("transfer") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                authViewModel = authViewModel,
                navController = navController
            )
        }

            TODO("Отредактировать навигацию с HomeScreen в TransferScreen ")
        composable("transfer") {
            TransferScreen(
                onBack = { navController.popBackStack() },
                onTransferSuccess = {
                    navController.popBackStack()
                },
                authViewModel = authViewModel
            )
        }
    }
}