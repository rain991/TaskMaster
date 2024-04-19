package com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField

@Composable
fun AcceptGroupDeletingDialog(group: Group, onDeclineButton: () -> Unit, onAcceptButton: () -> Unit) {
    var textField by remember { mutableStateOf("") }
    val localContext = LocalContext.current
    Dialog(
        onDismissRequest = onDeclineButton,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 2.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = AlertDialogDefaults.containerColor
                ),
            color = AlertDialogDefaults.containerColor
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Delete group", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    Text(text = "To delete ${group.name}, type group name below and accept deleting")
                }
                Spacer(modifier = Modifier.height(8.dp))
                GradientInputTextField(value = textField, label = "Group name") {
                    textField = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = { onDeclineButton() }) {
                        Text(text = "Decline")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        if (textField == group.name) {
                            onAcceptButton()
                        } else {
                            Toast.makeText(localContext, "Group name does not match", Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Text(text = "Accept")
                    }
                }
            }
        }
    }
}