package com.example.taskmaster.presentation.components.common.barsAndHeaders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R

@Composable
fun SimplifiedTopBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(), horizontalArrangement = Arrangement.End) {
        Image(
            painter = painterResource(id = R.drawable.appicon1),
            contentDescription = stringResource(R.string.app_icon),
            modifier = Modifier.size(48.dp)
        )
    }
}
