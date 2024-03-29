package com.example.taskmaster.presentation.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.presentation.components.common.TaskMasterScreenHeader

@Composable
fun MainExpenseScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TaskMasterScreenHeader(isTeacherScreen = false, userName = "") // VM params
        },bottomBar = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {

    }
}
}