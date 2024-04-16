package com.example.taskmaster.presentation.components.teacherComponents.createTasks

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.components.files.getFileName
import com.example.taskmaster.data.components.files.getFileSize
import com.example.taskmaster.data.constants.MAX_FILES_TO_SELECT
import com.example.taskmaster.data.constants.MAX_FILE_SIZE_BYTES
import com.example.taskmaster.data.viewModels.teacher.tasks.CreateTaskViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskComponent() {
    val localContext = LocalContext.current
    val viewModel = koinViewModel<CreateTaskViewModel>()
    val screenState = viewModel.createTaskScreenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val mimeTypeFilter = arrayOf("*/*")

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

//    val selectFileActivity =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
//            result.take(screenState.value.attachedFiles.size - MAX_FILES_TO_SELECT).forEach { fileUri ->
//                if (fileUri.getFileSize(localContext) <= MAX_FILE_SIZE_BYTES) {
//                    viewModel.addURI(fileUri)
//                } else {
//                    Toast.makeText(
//                        localContext,  // appContext previously
//                        "File ${fileUri.getFileName(localContext)} exceeds size limit 4MB",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
    val selectFileActivity =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
            val filesToAdd = result.take(5).filter { fileUri ->
                if (fileUri.getFileSize(localContext) <= MAX_FILE_SIZE_BYTES) {
                    true
                } else {
                    Toast.makeText(
                        localContext,
                        "File ${fileUri.getFileName(localContext)} exceeds size limit 4MB",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
            }

            val newAttachedFiles = if (screenState.value.attachedFiles != null) {
                (screenState.value.attachedFiles + filesToAdd).take(MAX_FILES_TO_SELECT)
            } else {
                filesToAdd.take(MAX_FILES_TO_SELECT)
            }

            viewModel.setAttachedFiles(newAttachedFiles)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        if (screenState.value.isGroupSelectorShown) {
            GroupSelector(viewModel)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AmountInput(
                focusRequester = focusRequester,
                controller = keyboardController,
                currentText = screenState.value.title,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 40.sp,
                    letterSpacing = 1.16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                emptyTextLabel = "Task title"
            ) {
                viewModel.setTitle(it)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start
        ) {
            AmountInput(
                focusRequester = focusRequester,
                controller = keyboardController,
                currentText = screenState.value.description,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 14.sp,
                    letterSpacing = 1.16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                emptyTextLabel = "Description"
            ) {
                viewModel.setDescription(it)
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { viewModel.setIsGroupSelectorShown(true) }) {
            Text("Groups")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.setDatePickerState(true) }) {
            Text("Deadline date")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.setTimePickerState(true) }) {
            Text("Deadline time")
        }
        if (screenState.value.datePickerState) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = {
                    viewModel.setDatePickerState(false)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.setDatePickerState(false)
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.setDatePickerState(false)
                        }
                    ) {
                        Text("CANCEL")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if (screenState.value.timePickerState) {
            val timePickerState = rememberTimePickerState()
            TimePickerDialog(onDismissRequest = { viewModel.setTimePickerState(false) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.setTimePickerState(false)
                        }
                    ) {
                        Text("OK")
                    }
                }, dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.setTimePickerState(false)
                        }
                    ) {
                        Text("CANCEL")
                    }
                }) {
                TimePicker(state = timePickerState)
            }
        }
        if (screenState.value.attachedFiles.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Attached files")
            Spacer(modifier = Modifier.height(4.dp))
            screenState.value.attachedFiles.forEach {
                FileRow(name = it.toString()) {
                    viewModel.deleteURI(it)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            OutlinedButton(onClick = {
                coroutineScope.launch{
                    selectFileActivity.launch(mimeTypeFilter)
                }
            }) {
                Text(text = "Attach files")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Assign task")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun AmountInput(
    focusRequester: FocusRequester,
    controller: SoftwareKeyboardController?,
    currentText: String,
    textStyle: TextStyle,
    emptyTextLabel: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .width(IntrinsicSize.Min)
            .padding(start = 6.dp),
        textStyle = textStyle,
        value = if (currentText == "") {
            emptyTextLabel
        } else {
            currentText
        },
        onValueChange = { newText ->
            if (newText == emptyTextLabel) {
                onValueChange("")
            } else {
                onValueChange(newText)
            }
        },
        keyboardActions = KeyboardActions(
            onDone = {
                controller?.hide()
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
private fun FileRow(name: String, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyMedium)
        Button(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete $name task file")
        }
    }
}