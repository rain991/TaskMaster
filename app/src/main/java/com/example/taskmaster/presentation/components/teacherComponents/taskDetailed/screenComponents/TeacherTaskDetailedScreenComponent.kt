package com.example.taskmaster.presentation.components.teacherComponents.taskDetailed.screenComponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskDetailedViewModel
import com.example.taskmaster.presentation.components.teacherComponents.taskDetailed.uiComponents.StudentAnswerCard

@Composable
fun TeacherTaskDetailedScreenComponent(viewModel: TeacherTaskDetailedViewModel) {
    val currentTask = viewModel.currentTask.collectAsState(initial = null)
    val taskRelatedAnswers = viewModel.taskRelatedAnswers
    val studentsList = viewModel.studentsList
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        viewModel.initializeAllTeacherRelatedAnswers()
    }
    if (currentTask.value == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No selected task")
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Task answers",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 32.sp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = currentTask.value?.name ?: "",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 26.sp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                var groupName = ""
                val group = currentTask.value?.groups?.firstOrNull()
                LaunchedEffect(key1 = Unit) {
                    if (group != null) {
                        groupName = viewModel.getGroupNameByIdentifier(group)
                    }
                }
                Text(
                    text = if (currentTask.value?.groups?.size == 1) {
                        groupName
                    } else {
                        "${currentTask.value?.groups?.size} groups assigned"
                    }, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Log.d(COMMON_DEBUG_TAG, "TeacherTaskDetailedScreenComponent: answers list size ${taskRelatedAnswers.size}")
            Log.d(COMMON_DEBUG_TAG, "TeacherTaskDetailedScreenComponent: currentRelatedTask $currentTask")
            Log.d(COMMON_DEBUG_TAG, "TeacherTaskDetailedScreenComponent: students list size ${studentsList.size}")
            LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                items(count = studentsList.size) { studentIndex ->
                    val currentStudent = studentsList[studentIndex]
                    val currentAnswer = taskRelatedAnswers.find { it.studentUid == currentStudent.uid }
                    StudentAnswerCard(
                        studentName = currentStudent.name,
                        studentSurname = currentStudent.surname,
                        isAssigned = currentAnswer != null,
                        onCardClick = {

                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }
    }
}