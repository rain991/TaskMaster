package com.example.taskmaster.presentation.components.studentComponents.task.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField

// Probably needs additional scrollable column inside
@Composable
fun SubmitTaskStudent(
    task: Task,
    vararg fileNames: String,
    onBackButton: () -> Unit,
    onDownloadAll: () -> Unit
) {  // functions will be deleted and got in straight way from Viewmodel

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        var teacherName by remember{ mutableStateOf("") }
        LaunchedEffect(key1 = Unit) {

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CircleWithText(text = task.name.trim().substring(0, minOf(task.name.length, 2)), modifier = Modifier.size(40.dp))
            Column(Modifier.wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = task.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = task.teacher, style = MaterialTheme.typography.titleSmall)  // WARNING ITS UID
            }
            Button(onClick = { onBackButton() }, shape = RoundedCornerShape(8.dp), modifier = Modifier.wrapContentWidth()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.scale(1.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TaskRow(*fileNames)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = { onDownloadAll() }) {
                Text(text = "Download all", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        GradientInputTextField(value = "", label = "") { // FROM VM

        }
        Spacer(modifier = Modifier.height(8.dp))
        StudentFilesRow(/*params*/) // FROM VM
        Spacer(modifier = Modifier.height(8.dp))
        ButtonsRow()
    }
}

@Composable
private fun TaskRow(
    vararg names: String
) { // onDownlaodButtonClick dla downloadAll, jeszcze nie wiem jak zrobic pobieranie kazdego pliku kliknieciem, mozna miec tylko 1 vararg w parametrach
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Task files:", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        for (name in names) {
            Text(text = name, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun StudentFilesRow(
    vararg names: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "Your files:", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        for (name in names) {
            Text(text = name, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun ButtonsRow() {  // VM in params
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            modifier = Modifier.scale(0.8f),
            onClick = { /*TODO*/ }
        ) {
            Text("Unattach files")
        }
        OutlinedButton(
            modifier = Modifier.scale(0.8f),
            onClick = {
                /*TODO*/
            }) {
            Text("Attach files")
        }
        FilledTonalButton(onClick = { /*TODO*/ }, modifier = Modifier.scale(0.8f)) {
            Text("Submit")
        }
    }
}


