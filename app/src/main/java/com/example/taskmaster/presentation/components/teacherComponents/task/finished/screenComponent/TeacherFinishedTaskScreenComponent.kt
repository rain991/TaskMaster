package com.example.taskmaster.presentation.components.teacherComponents.task.finished.screenComponent

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskListViewModel
import com.example.taskmaster.presentation.components.teacherComponents.task.TeacherTaskCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun TeacherFinishedTaskScreenComponent() {
    val taskScreenViewModel = koinViewModel<TeacherTaskListViewModel>()
    val lazyListState = rememberLazyListState()
    val finishedTaskList = taskScreenViewModel.finishedTasksList
    val teacherUidToNameMap = taskScreenViewModel.teacherUidToNameMap
    val groupIdentifierToNameMap = taskScreenViewModel.groupIdToNameMap
    LaunchedEffect(key1 = Unit) {
        taskScreenViewModel.initializeTeacherUidToNameMapForFinishedTasks()
        taskScreenViewModel.initializeGroupIdToNameMapForFinishedTasks()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Finished Tasks", style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold))
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (finishedTaskList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "You don't have finished tasks")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Easily create new one using Create Task screen")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                    items(count = finishedTaskList.size) {
                        val currentTaskItem = finishedTaskList[it]
                        val groupMessage = if (currentTaskItem.groups.size > 1) {
                            "${currentTaskItem.groups.size} groups was assigned"
                        } else {
                            groupIdentifierToNameMap[currentTaskItem.groups.first()]
                        }
                        Log.d(COMMON_DEBUG_TAG, "FinishedTaskScreenComponent:  groupIdentifierToNameMap size is ${groupIdentifierToNameMap.size}")
                        TeacherTaskCard(
                            teacherName = teacherUidToNameMap[currentTaskItem.teacher].toString(),
                            taskName = currentTaskItem.name,
                            groupName = groupMessage ?: "",
                            endDate = currentTaskItem.endDate
                        ) {

                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}