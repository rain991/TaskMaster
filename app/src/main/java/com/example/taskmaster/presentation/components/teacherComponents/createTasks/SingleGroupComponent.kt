package com.example.taskmaster.presentation.components.teacherComponents.createTasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText

@Composable
fun SingleGroupComponent(groupName: String, onComponentClick: () -> Unit, isSelected: Boolean) {
    val localContext = LocalContext.current
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 4.dp)
            .clickable { onComponentClick() }, verticalAlignment = Alignment.CenterVertically
    ) {
        CircleWithText(text = groupName.substring(0..1), modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = groupName, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }
}