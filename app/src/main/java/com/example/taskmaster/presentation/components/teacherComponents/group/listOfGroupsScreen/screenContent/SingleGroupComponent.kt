package com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.example.taskmaster.presentation.components.common.other.TextButtonWithClipboard

@Composable
fun SingleGroupComponent(group: Group, onButtonClickDeleteGroup: () -> Unit) {
    val localContext = LocalContext.current
    Row(modifier = Modifier
        .wrapContentHeight()
        .padding(horizontal = 4.dp)
        .clickable { onButtonClickDeleteGroup() }, verticalAlignment = Alignment.CenterVertically
    ) {
        CircleWithText(text = group.name.substring(0..1), modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier.wrapContentHeight()) {
            Text(text = group.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Text(text = "identifier: ")
                TextButtonWithClipboard(text = group.identifier)
            }
        }




        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
    }
}