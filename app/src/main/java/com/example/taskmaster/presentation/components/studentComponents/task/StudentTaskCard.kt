package com.example.taskmaster.presentation.components.studentComponents.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.example.taskmaster.presentation.other.getTimeRemaining
import java.time.LocalDate

@Composable
fun StudentTaskCard(
    teacherName: String,
    taskName: String,
    groupName: String,
    endDate: Long,
    onSubmitTask: () -> Unit,
    isSubmitted: Boolean = false
) {  // for expired and current tasks, also for already submitted
    val trimmedGroupName = groupName.trim().substring(0, minOf(groupName.length, 2))
    val timeLeft = getTimeRemaining(endDate)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                    Text(
                        text = if (!isSubmitted) {
                            timeLeft
                        } else {
                            "Already submitted"
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(0.2f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(modifier = Modifier.wrapContentWidth(), Arrangement.Center) {
                    if (!isSubmitted) {
                        Button(onClick = {
                            onSubmitTask()
                        }, shape = RoundedCornerShape(8.dp), modifier = Modifier.wrapContentWidth()) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add answer for task",
                                modifier = Modifier.scale(1.3f)
                            )
                        }
                    } else {
                        Button(onClick = { }, shape = RoundedCornerShape(8.dp), modifier = Modifier.wrapContentWidth()) {  // prob onClick to change answer
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "already submitted",
                                modifier = Modifier.scale(1.3f)
                            )
                        }
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}


@Preview(showSystemUi = true)
@Composable
private fun prev() {
    StudentTaskCard(
        teacherName = "Ivan S",
        taskName = "Sprawozdanie 1",
        groupName = "dzsad",
        endDate = LocalDate.now().toEpochDay() + 5000,
        onSubmitTask = { /*TODO*/ })
}