package com.example.taskmaster.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.taskmaster.presentation.components.common.loginComponents.ResetPasswordComponent

@Composable
fun ResetPasswordScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {

        },bottomBar = { },
        floatingActionButton = {}
    ) {
        ResetPasswordComponent(paddingValues = it, navController = navController)
    }
}