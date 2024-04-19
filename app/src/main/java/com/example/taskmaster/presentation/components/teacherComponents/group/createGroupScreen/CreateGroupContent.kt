package com.example.taskmaster.presentation.components.teacherComponents.group.createGroupScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import com.example.taskmaster.presentation.components.teacherComponents.group.groupDetailedScreen.SubmittedToGroupStudentComponent

@Composable
fun CreateGroupContent(
    listOfStudents: List<Student>,
    onGroupNameTextFieldChanges: (String) -> Unit,
    onDeleteStudentFromUncreatedGroup: (Student) -> Unit,
    onCreateGroup: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxWidth()) {
        GradientInputTextField(value = "Group name", label = "mock") {  // from VM
            onGroupNameTextFieldChanges(it)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
            items(count = listOfStudents.size) { index ->
                val currentStudent = listOfStudents[index]
                SubmittedToGroupStudentComponent(student = currentStudent) {
                    onDeleteStudentFromUncreatedGroup(currentStudent)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(0.36f)) {
            Button(onClick = { onCreateGroup() }) {
                Text(text = "Create group!", style = MaterialTheme.typography.titleSmall)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}