package com.example.taskmaster.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R

@Composable
fun ScreenPlaceholder() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { }, bottomBar = { },
        floatingActionButton = { }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.appicon1),
                contentDescription = stringResource(R.string.app_icon),
                modifier = Modifier.size(220.dp)
            )
        }
    }
}