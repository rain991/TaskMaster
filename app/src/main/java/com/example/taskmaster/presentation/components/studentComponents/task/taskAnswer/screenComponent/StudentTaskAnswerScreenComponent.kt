package com.example.taskmaster.presentation.components.studentComponents.task.taskAnswer.screenComponent

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.R
import com.example.taskmaster.data.components.files.getFileSize
import com.example.taskmaster.data.constants.ANSWER_MAX_LENGTH
import com.example.taskmaster.data.constants.FILE_NAME_SUBSTRING_EDGE
import com.example.taskmaster.data.constants.MAX_FILES_TO_SELECT
import com.example.taskmaster.data.constants.MAX_FILE_SIZE_BYTES
import com.example.taskmaster.data.viewModels.student.answers.StudentAnswerScreenViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import kotlinx.coroutines.launch

@Composable
fun StudentTaskAnswerScreenComponent(viewModel: StudentAnswerScreenViewModel) {
    val currentScreenState = viewModel.studentAnswerScreenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current
    val mimeTypeFilter = arrayOf("*/*")
    val selectFileActivity = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
        val filesToAdd = result.take(MAX_FILES_TO_SELECT).filter { fileUri ->
            if (fileUri.getFileSize(localContext) <= MAX_FILE_SIZE_BYTES) {
                true
            } else {
                Toast.makeText(
                    localContext,
                    localContext.getText(R.string.create_task_file_size_error),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }
        val newAttachedFiles = if (currentScreenState.value.studentFiles.isNotEmpty()) {
            (currentScreenState.value.studentFiles + filesToAdd).take(MAX_FILES_TO_SELECT)
        } else {
            filesToAdd.take(MAX_FILES_TO_SELECT)
        }
        viewModel.setAnswerFiles(newAttachedFiles)
    }


    if (currentScreenState.value.warningMessage != null) {
        Toast.makeText(localContext, currentScreenState.value.warningMessage?.asString(localContext), Toast.LENGTH_SHORT).show()
        viewModel.deleteWarningMessage()
    }

    if (currentScreenState.value.currentTask == null) {
        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.student_task_answer_no_selected_task), style = MaterialTheme.typography.titleMedium)
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
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(Modifier.wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = currentScreenState.value.currentTask?.name ?: "",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold)
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
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleSmall
                )
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.student_task_answer_task_files),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            if (currentScreenState.value.currentTask?.relatedFilesURL?.isNotEmpty() == true) {

                currentScreenState.value.currentTask?.relatedFilesURL?.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(
                                R.string.student_task_answer_task_current_file,
                                index,
                                item.substring(0, FILE_NAME_SUBSTRING_EDGE)
                            ),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        coroutineScope.launch {
                            viewModel.downloadTaskFiles()
                        }
                    }) {
                        Text(text = stringResource(R.string.download), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.there_is_no_task_files),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = stringResource(R.string.student_task_answer_your_answer), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            GradientInputTextField(value = currentScreenState.value.studentAnswer, label = stringResource(R.string.student_task_answer_answer_goes_here), onValueChange = {
               if(it.length < ANSWER_MAX_LENGTH){
                   viewModel.setStudentAnswer(it)
               }
            }, maxLines = 8)
            Spacer(modifier = Modifier.height(16.dp))



            Text(text = stringResource(R.string.student_task_answer_your_attached_files), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            if (currentScreenState.value.studentFiles.isNotEmpty()) {
                currentScreenState.value.studentFiles.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.student_task_answer_task_current_file),
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
                        text = stringResource(R.string.student_task_answer_no_attached_files_message),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.End
            ) {
                if (currentScreenState.value.studentFiles.isNotEmpty()) {
                    OutlinedButton(
                        modifier = Modifier,
                        onClick = { viewModel.unAttachStudentFiles() }
                    ) {
                        Text(stringResource(R.string.unattach_files))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                OutlinedButton(
                    modifier = Modifier,
                    onClick = {
                        selectFileActivity.launch(mimeTypeFilter)
                    }) {
                    Text(stringResource(R.string.attach_files))
                }
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalButton(onClick = {
                    coroutineScope.launch {
                        viewModel.addAnswer(localContext)
                    }
                }, modifier = Modifier) {
                    Text(stringResource(R.string.submit))
                }
            }
        }
    }
}