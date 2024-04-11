package com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText

@Composable
fun SingleGroupComponent(group: Group, onButtonClickDeleteGroup: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onButtonClickDeleteGroup() }, verticalAlignment = Alignment.CenterVertically) {
        CircleWithText(text = group.name.substring(0..1), modifier = Modifier.wrapContentHeight())
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = group.name, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
    }
}