package com.example.taskmaster.presentation.components.studentComponents.group.uiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText


@Composable
fun StudentGroupSingleComponent(group: Group, teacherName: String, onComponentClick: () -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 4.dp)
            .clickable { onComponentClick() }, verticalAlignment = Alignment.CenterVertically
    ) {
        CircleWithText(text = group.name.substring(0..1), modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier.wrapContentHeight()) {
            Text(text = group.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = teacherName, style = MaterialTheme.typography.titleMedium)
        }
    }
}