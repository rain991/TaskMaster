package com.example.taskmaster.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navController = rememberNavController()
//    LaunchedEffect(key1 = loginCount) {
//        if (loginCount.value == 0) {
//            navController.navigate(Screen.LoginScreen.route)
//        } else {
//            navController.navigate(Screen.MainScreen.route)
//        }
//    }
//    NavHost(
//        navController = navController,
//        startDestination = Screen.MainScreen.route
//    ) {
//
//    }
}