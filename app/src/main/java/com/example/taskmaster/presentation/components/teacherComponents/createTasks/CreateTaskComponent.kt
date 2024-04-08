package com.example.taskmaster.presentation.components.teacherComponents.createTasks

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.viewModels.teacher.CreateTaskViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateTaskComponent() {
    val viewModel = koinViewModel<CreateTaskViewModel>()
    val screenState = viewModel.createTaskScreenState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    Column(modifier = Modifier.fillMaxSize()) {
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

        Button(onClick = { /*TODO*/ }) {
            Text("Choose groups")
        }

        Button(onClick = { /*TODO*/ }) {
            Text("Choose end date")
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
        value = if (currentText != "") {
            currentText
        } else {
            emptyTextLabel
        },
        onValueChange = { newText ->
            onValueChange(newText)
        },
        keyboardActions = KeyboardActions(
            onDone = {
                controller?.hide()
                focusManager.clearFocus()
            }
        ),
        maxLines = 1,
    )
}