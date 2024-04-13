package com.example.taskmaster.presentation.components.teacherComponents.group.groupDetailedScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupDetailedScreenComponent(onDeleteStudent: (Student) -> Unit) {
    val lazyListState = rememberLazyListState()
    val viewModel = koinViewModel<GroupDetailedScreenViewModel>()
    val currentDetailedGroup = viewModel.currentDetailedGroup.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val warningMessage = viewModel.warningMessage.collectAsState()
    val searchedStudentsList = viewModel.searchedStudentsList
    val listOfGroupStudents = viewModel.listOfGroupStudents
    if (currentDetailedGroup.value == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "There is no current selected group", style = MaterialTheme.typography.titleSmall)
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = currentDetailedGroup.value!!.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                items(
                    if (searchText.value.isNotEmpty()) {
                        searchedStudentsList.size
                    } else {
                        listOfGroupStudents.size
                    }
                ) { index ->
                    val currentItem = if (searchText.value.isNotEmpty()) {
                        searchedStudentsList[index]
                    } else {
                        listOfGroupStudents[index]
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = currentItem.email)
                        Button(onClick = {viewModel.deleteStudentFromGroup()}) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "delete ${currentItem.email} student from group")
                        }

                    }
                }
            }
        }
    }
}