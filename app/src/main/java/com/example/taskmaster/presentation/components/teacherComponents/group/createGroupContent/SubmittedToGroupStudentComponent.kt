package com.example.taskmaster.presentation.components.teacherComponents.group.createGroupContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText

@Composable
fun SubmittedToGroupStudentComponent(student: Student, onRowClickDeleteStudent: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onRowClickDeleteStudent() }, verticalAlignment = Alignment.CenterVertically) {
        CircleWithText(text = student.name.substring(0..1), modifier = Modifier.wrapContentHeight())
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = student.name, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
    }
}