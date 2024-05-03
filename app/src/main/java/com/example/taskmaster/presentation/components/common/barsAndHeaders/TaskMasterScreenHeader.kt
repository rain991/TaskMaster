package com.example.taskmaster.presentation.components.common.barsAndHeaders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R

@Composable
fun TaskMasterScreenHeader(isTeacherScreen: Boolean, userName: String, unfinishedTasks: Int = 0) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(text = stringResource(R.string.screen_header_greetings, userName), style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = if (isTeacherScreen) {
                    stringResource(R.string.screen_header_additional)
                } else {
                    stringResource(R.string.screen_header_additional_student, unfinishedTasks)
                },
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
