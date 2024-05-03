package com.example.taskmaster.presentation.components.teacherComponents.task.unfinished.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskDetailedViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskListViewModel
import com.example.taskmaster.presentation.components.teacherComponents.task.TeacherTaskCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun TeacherTaskScreenComponent(navController: NavController, teacherTaskDetailedViewModel: TeacherTaskDetailedViewModel) {
    val taskScreenViewModel = koinViewModel<TeacherTaskListViewModel>()
    val lazyListState = rememberLazyListState()
    val unFinishedTaskList = taskScreenViewModel.unfinishedTasksList
    val teacherUidToNameMap = taskScreenViewModel.teacherUidToNameMap
    val groupIdentifierToNameMap = taskScreenViewModel.groupIdToNameMap
    LaunchedEffect(key1 = Unit) {
        taskScreenViewModel.initializeGroupIdToNameMapForUnfinishedTasks()
        taskScreenViewModel.initializeTeacherUidToNameMapForUnfinishedTasks()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(id = R.string.tasks_header), style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (unFinishedTaskList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.teacher_task_message1))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(R.string.teacher_task_message2))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                    items(count = unFinishedTaskList.size) {
                        val currentTaskItem = unFinishedTaskList[it]
                        val groupMessage = if (currentTaskItem.groups.size > 1) {
                            stringResource(R.string.teacher_task_groups_was_assigned, currentTaskItem.groups.size)
                        } else {
                            groupIdentifierToNameMap[currentTaskItem.groups[0]]
                        }
                        TeacherTaskCard(
                            teacherName = teacherUidToNameMap[currentTaskItem.teacher].toString(),
                            taskName = currentTaskItem.name,
                            groupName = groupMessage ?: "",
                            endDate = currentTaskItem.endDate
                        ) {
                            teacherTaskDetailedViewModel.setCurrentTask(currentTaskItem)
                            navController.navigate(Screen.TaskDetailedScreen.route)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}