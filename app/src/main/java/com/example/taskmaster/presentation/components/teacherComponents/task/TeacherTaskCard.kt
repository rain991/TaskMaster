package com.example.taskmaster.presentation.components.teacherComponents.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.example.taskmaster.presentation.other.getTimeRemaining

@Composable
fun TeacherTaskCard(
    teacherName: String,
    taskName: String,
    groupName: String,
    endDate: Long,
    onRowClick: () -> Unit
) {
    val localContext = LocalContext.current
    val trimmedTaskName = taskName.trim().substring(0, minOf(groupName.length, 3))
    val timeLeft = getTimeRemaining(localContext,endDate)
    val isEndedTask = System.currentTimeMillis() > endDate
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onRowClick() }
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    CircleWithText(text = trimmedTaskName, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Column(Modifier.wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = taskName, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = teacherName, style = MaterialTheme.typography.titleSmall)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Group: $groupName")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (!isEndedTask) {
                            timeLeft
                        } else {
                            stringResource(R.string.teacher_task_card_this_task_is_ended)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}