package com.example.taskmaster.presentation.components.teacherComponents.taskDetailed.screenComponents

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherAnswerViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskDetailedViewModel
import com.example.taskmaster.presentation.components.teacherComponents.taskDetailed.uiComponents.StudentAnswerCard

@Composable
fun TeacherTaskDetailedScreenComponent(screenViewModel: TeacherTaskDetailedViewModel, answerDetailedViewModel : TeacherAnswerViewModel, navController : NavController) {
    val currentTask = screenViewModel.currentTask.collectAsState(initial = null)
    val taskRelatedAnswers = screenViewModel.taskRelatedAnswers
    val studentsList = screenViewModel.studentsList
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        screenViewModel.initializeAllTeacherRelatedAnswers()
    }
    if (currentTask.value == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.teacher_task_detailed_no_selected_task))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.teacher_task_detailed_task_answers),
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
                        groupName = screenViewModel.getGroupNameByIdentifier(group)
                    }
                }
                Text(
                    text = if (currentTask.value?.groups?.size == 1) {
                        groupName
                    } else {
                        stringResource(R.string.teacher_task_detailed_groups_assigned, currentTask.value?.groups?.size ?: 0)
                    }, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                items(count = studentsList.size) { studentIndex ->
                    val currentStudent = studentsList[studentIndex]
                    val currentAnswer = taskRelatedAnswers.find { it.studentUid == currentStudent.uid }
                    StudentAnswerCard(
                        studentName = currentStudent.name,
                        studentSurname = currentStudent.surname,
                        isAssigned = currentAnswer != null,
                        onCardClick = {
                            answerDetailedViewModel.setCurrentStudentAnswer(currentAnswer)
                            answerDetailedViewModel.setFetchedStudentName(currentStudent.name + " " + currentStudent.surname)
                            navController.navigate(Screen.AnswerDetailedScreen.route)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}