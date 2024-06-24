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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.R
import com.example.taskmaster.data.components.files.getFileName
import com.example.taskmaster.data.components.files.getFileSize
import com.example.taskmaster.data.constants.MAX_FILES_TO_SELECT
import com.example.taskmaster.data.constants.MAX_FILE_SIZE_BYTES
import com.example.taskmaster.data.constants.TASK_DESCRIPTION_MAX_LENGTH
import com.example.taskmaster.data.constants.TASK_NAME_MAX_LENGTH
import com.example.taskmaster.data.constants.formatDateWithoutYear
import com.example.taskmaster.data.viewModels.teacher.tasks.CreateTaskViewModel
import com.example.taskmaster.presentation.UiText.UiText
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.Date


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
    val selectFileActivity =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { result ->
            val filesToAdd = result.take(MAX_FILES_TO_SELECT).filter { fileUri ->
                if (fileUri.getFileSize(localContext) <= MAX_FILE_SIZE_BYTES) {
                    true
                } else {
                    Toast.makeText(
                        localContext,
                        localContext.getString(R.string.create_task_file_size_error, fileUri.getFileName(localContext)),
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
            }

            val newAttachedFiles = if (screenState.value.attachedFiles.isNotEmpty()) {
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
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                if (it.length < TASK_NAME_MAX_LENGTH) {
                    viewModel.setTitle(it)
                }
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
                )
            ) {
                if (it.length < TASK_DESCRIPTION_MAX_LENGTH) {
                    viewModel.setDescription(it)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { viewModel.setIsGroupSelectorShown(true) }) {
            Text(stringResource(id = R.string.groups))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.setDatePickerState(true) }) {
            Text(stringResource(R.string.create_task_deadline_date))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.setTimePickerState(true) }) {
            Text(stringResource(R.string.create_task_deadline_time))
        }
        if (screenState.value.selectedDate != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.create_task_selected_date), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = formatDateWithoutYear(Date(screenState.value.selectedDate!!)))
        }
        if (screenState.value.datePickerState) {
            val datePickerState = rememberDatePickerState(screenState.value.selectedDate)
            DatePickerDialog(
                onDismissRequest = {
                    viewModel.setDatePickerState(false)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.setDatePickerState(false)
                            if (datePickerState.selectedDateMillis != null) {
                                viewModel.setSelectedDate(datePickerState.selectedDateMillis!!)
                            }
                        }
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.setDatePickerState(false)
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if (screenState.value.timePickerState) {
            val initialDateMillis = screenState.value.selectedDate ?: System.currentTimeMillis()
            val calendar = Calendar.getInstance().apply {
                timeInMillis = initialDateMillis
            }

            val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
            val initialMinute = calendar.get(Calendar.MINUTE)

            val timePickerState = rememberTimePickerState(initialHour = initialHour, initialMinute = initialMinute, is24Hour = true)
            TimePickerDialog(onDismissRequest = { viewModel.setTimePickerState(false) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.setTimePickerState(false)
                            if (screenState.value.selectedDate != null) {
                                val selectedDate = screenState.value.selectedDate!!
                                val selectedTimeMillis = (timePickerState.hour * 60 + timePickerState.minute) * 60 * 1000L
                                val selectedDateTimeMillis = selectedDate + selectedTimeMillis
                                viewModel.setSelectedDate(selectedDateTimeMillis)
                            } else {
                                viewModel.setWarningMessage(UiText(R.string.create_task_select_date_first_message))
                            }
                        }
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                }, dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.setTimePickerState(false)
                        }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }) {
                TimePicker(state = timePickerState)
            }
        }
        if (screenState.value.attachedFiles.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.create_task_attached_files))
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
                coroutineScope.launch {
                    selectFileActivity.launch(mimeTypeFilter)
                }
            }) {
                Text(text = stringResource(R.string.create_task_attach_files))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.createTask(localContext)
                }
            }) {
                Text(text = stringResource(R.string.create_task_assign_task))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (screenState.value.warningMessage != null) {
            Toast.makeText(localContext, screenState.value.warningMessage?.asString(localContext), Toast.LENGTH_SHORT).show()
            viewModel.deleteWarningMessage()
        }
    }
}

@Composable
private fun AmountInput(
    focusRequester: FocusRequester,
    controller: SoftwareKeyboardController?,
    currentText: String,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .width(IntrinsicSize.Min)
            .padding(start = 6.dp),
        textStyle = textStyle,
        value = currentText,
        onValueChange = { newText ->
            onValueChange(newText)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                controller?.hide()
                focusManager.clearFocus()
            }
        ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
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
        Button(onClick = { onDeleteClick() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.create_task_delete_task_file_CD, name))
        }
    }
}