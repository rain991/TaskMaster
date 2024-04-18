package com.example.taskmaster.presentation.components.teacherComponents.task.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.viewModels.teacher.tasks.TaskListViewModel
import com.example.taskmaster.presentation.components.teacherComponents.task.TeacherTaskCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreenComponent() {
    val taskScreenViewModel = koinViewModel<TaskListViewModel>()
    val lazyListState = rememberLazyListState()
    val unFinishedTaskList = taskScreenViewModel.unfinishedTasksList


    if (unFinishedTaskList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "You don't have unfinished tasks")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Easily create new one using Create Task screen")
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                items(count = unFinishedTaskList.size) {
                    val currentTaskItem = unFinishedTaskList[it]
                    TeacherTaskCard(teacherName =, taskName =, groupName =, endDate =) {

                    }
                }
            }
        }
    }


}