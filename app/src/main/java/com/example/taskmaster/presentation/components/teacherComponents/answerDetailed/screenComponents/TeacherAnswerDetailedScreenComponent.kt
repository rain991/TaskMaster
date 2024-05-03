package com.example.taskmaster.presentation.components.teacherComponents.answerDetailed.screenComponents

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.constants.ANSWER_MAX_LENGTH
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherAnswerViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TeacherAnswerDetailedScreenComponent(answerDetailedViewModel: TeacherAnswerViewModel) {
    val screenState = answerDetailedViewModel.teacherAnswerScreenState.collectAsState()
    val localContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if(screenState.value.warningMessage!=null){
            Toast.makeText(localContext, screenState.value.warningMessage,Toast.LENGTH_SHORT).show()
            answerDetailedViewModel.deleteWarningMessage()
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Student answer",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = screenState.value.fetchedStudentName ?: "",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 26.sp)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = screenState.value.fetchedTaskName ?: "",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (screenState.value.currentStudentAnswer == null) {
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "This student has not assigned any answer",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Student answer:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = screenState.value.currentStudentAnswer?.answer ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Student attached ${screenState.value.currentStudentAnswer?.fileUrls?.size} files",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        answerDetailedViewModel.downloadStudentFiles(localContext)
                    }
                }
            }) {
                Text(text = "Download all")
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            GradientInputTextField(value = screenState.value.teacherAnswer, label = "Your comment") {
                if (it.length < ANSWER_MAX_LENGTH) {
                    answerDetailedViewModel.setTeacherAnswer(it)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            GradientInputTextField(
                value = screenState.value.teacherGrade.toString(),
                label = "Grade",
                keyboardType = KeyboardType.Number
            ) {
                answerDetailedViewModel.setTeacherGrade(it.toFloat())
            }
            Spacer(modifier = Modifier.weight(1f))
            //Log.d(COMMON_DEBUG_TAG, "TeacherTaskDetailedScreenComponent: answers list size ${taskRelatedAnswers.size}")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedButton(onClick = { coroutineScope.launch{
                    answerDetailedViewModel.sentBack()
                  //  Toast.makeText(localContext, "Sent back",Toast.LENGTH_SHORT).show()
                } }) {
                    Text(text = "Send back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalButton(onClick = {coroutineScope.launch {
                    answerDetailedViewModel.grade()
                  //  Toast.makeText(localContext, "Added grade",Toast.LENGTH_SHORT).show()
                } }) {
                    Text(text = "Grade")
                }
            }
        }
    }
}