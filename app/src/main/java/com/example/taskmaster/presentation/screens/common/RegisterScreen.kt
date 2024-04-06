package com.example.taskmaster.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.taskmaster.presentation.components.common.loginComponents.RegisterScreenComponent

@Composable
fun RegisterScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {

        },bottomBar = { },
        floatingActionButton = {}
    ) {
        RegisterScreenComponent(paddingValues = it, navController = navController)
    }
}