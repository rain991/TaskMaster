package com.example.taskmaster.presentation.components.studentComponents.task.finished.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText

@Composable
fun StudentFinishedTaskCard(
    teacherName: String,
    taskName: String,
    groupName: String,
    grade: String?,
    isSubmitted: Boolean
) {  // for expired and current tasks, also for already submitted
    val trimmedGroupName = groupName.trim().substring(0, minOf(groupName.length, 2))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.weight(0.8f)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    CircleWithText(text = trimmedGroupName, modifier = Modifier.size(40.dp))
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
                    if (isSubmitted) {
                        Text(
                            text = if (grade != null) {
                                "Grade: $grade"
                            } else {
                                "Is not graded yet"
                            }, style = MaterialTheme.typography.titleSmall
                        )
                    } else {
                        Text(
                            text = "Grade: you have not submitted any answer", style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSubmitted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Task is submitted",
                        modifier = Modifier.scale(1.3f)
                    )
                } else {
                    Box(modifier = Modifier.wrapContentWidth()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Task is not submitted",
                            modifier = Modifier.scale(1.3f)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}