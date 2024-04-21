package com.example.taskmaster.presentation.components.studentComponents.task.unfinished.screenComponent

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.viewModels.student.tasks.StudentTasksViewModel
import com.example.taskmaster.presentation.components.studentComponents.task.common.StudentTaskCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentTaskScreenComponent() {
    val localContext = LocalContext.current
    val studentTaskScreenViewModel = koinViewModel<StudentTasksViewModel>()
    val lazyListState = rememberLazyListState()
    val unFinishedTaskList = studentTaskScreenViewModel.unfinishedTasksList
    val teacherUidToNameMap = studentTaskScreenViewModel.teacherUidToNameMap
    val warningMessage = studentTaskScreenViewModel.warningMessage.collectAsState()
    LaunchedEffect(key1 = Unit) {
        studentTaskScreenViewModel.initializeTeacherUidToNameMapForUnfinishedTasks()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Tasks", style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (unFinishedTaskList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "You don't have unfinished tasks")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "You see all task assigned to your groups")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
                    items(count = unFinishedTaskList.size) { itemIndex ->
                        val currentTaskItem = unFinishedTaskList[itemIndex]
                        val taskRelatedGroup = studentTaskScreenViewModel.studentGroups.firstOrNull { group ->
                            studentTaskScreenViewModel.unfinishedTasksList
                                .flatMap { it.groups }
                                .contains(group.identifier)
                        }
                        val groupName = taskRelatedGroup?.name ?: ""
                        StudentTaskCard(
                            teacherName = teacherUidToNameMap[currentTaskItem.teacher] ?: "",
                            taskName = currentTaskItem.name,
                            groupName = groupName,
                            endDate = currentTaskItem.endDate,
                            onSubmitTask = { /*    */ })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        if (warningMessage.value != null) {
            Toast.makeText(localContext, warningMessage.value, Toast.LENGTH_SHORT).show()
            studentTaskScreenViewModel.deleteWarningMessage()
        }
    }
}