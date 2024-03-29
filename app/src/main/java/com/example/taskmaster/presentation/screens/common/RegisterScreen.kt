package com.example.taskmaster.presentation.screens.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmaster.presentation.components.common.loginComponents.LoginScreenComponent
import com.example.taskmaster.presentation.components.common.loginComponents.RegisterScreenComponent

@Composable
fun RegisterScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {

        },bottomBar = { },
        floatingActionButton = {}
    ) {
        RegisterScreenComponent(it)
    }
}