package com.example.taskmaster.presentation.components.studentComponents.task.taskAnswer.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.constants.FILE_NAME_SUBSTRING_EDGE
import com.example.taskmaster.data.viewModels.student.answers.StudentAnswerScreenViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentTaskAnswerScreenComponent() {
    val viewModel = koinViewModel<StudentAnswerScreenViewModel>()
    val currentScreenState = viewModel.studentAnswerScreenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    if (currentScreenState.value.currentTask == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "There is no selected task to answer", style = MaterialTheme.typography.titleSmall)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            LaunchedEffect(key1 = Unit) {
                viewModel.fetchTeacherName()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Add answer", style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = currentScreenState.value.currentTask?.name ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentScreenState.value.fetchedTeacherName ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Description", style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = currentScreenState.value.currentTask?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Visible
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.8f))
            Spacer(modifier = Modifier.height(4.dp))

            if (currentScreenState.value.currentTask?.relatedFilesURL?.isNotEmpty() == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Task files:",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                currentScreenState.value.currentTask?.relatedFilesURL?.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "File $index : ${item.substring(0, FILE_NAME_SUBSTRING_EDGE)}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "There is no task files",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.downloadTaskFiles()
                    }
                }) {
                    Text(text = "Download", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Your answer:", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            GradientInputTextField(value = currentScreenState.value.studentAnswer, label = "Answer goes here", onValueChange = {
                viewModel.setStudentAnswer(it)
            }, maxLines = 8)
            Spacer(modifier = Modifier.height(16.dp))



            Text(text = "Your attached files:", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentScreenState.value.studentFiles.isNotEmpty()) {
                currentScreenState.value.studentFiles.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "File $index : ${item.toString().substring(0, FILE_NAME_SUBSTRING_EDGE)}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "You haven't attached files yet",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    modifier = Modifier.scale(0.8f),
                    onClick = { viewModel.unAttachStudentFiles() }
                ) {
                    Text("Unattach files")
                }
                OutlinedButton(
                    modifier = Modifier.scale(0.8f),
                    onClick = {
                        /*TODO*/
                    }) {
                    Text("Attach files")
                }
                FilledTonalButton(onClick = {
                    coroutineScope.launch {
                        viewModel.addAnswer()
                    }
                }, modifier = Modifier.scale(0.8f)) {
                    Text("Submit")
                }
            }
        }
    }
}