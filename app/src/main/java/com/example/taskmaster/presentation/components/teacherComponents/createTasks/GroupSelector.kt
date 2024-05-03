package com.example.taskmaster.presentation.components.teacherComponents.createTasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.taskmaster.R
import com.example.taskmaster.data.viewModels.teacher.tasks.CreateTaskViewModel

@Composable
fun GroupSelector(
    viewModel: CreateTaskViewModel
) {
    val screenState = viewModel.createTaskScreenState.collectAsState()
    val lazyListState = rememberLazyListState()
    Dialog(
        onDismissRequest = { viewModel.setIsGroupSelectorShown(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.64f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.groups), style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth(0.64f)) {
                    items(screenState.value.listOfGroupNames.size) {
                        val currentGroup = screenState.value.listOfGroupNames[it]
                        val isSelected = screenState.value.listOfSelectedGroupNames.contains(currentGroup)
                        Spacer(modifier = Modifier.height(8.dp))
                        SingleGroupComponent(
                            groupName = currentGroup,
                            onComponentClick = {
                                if (isSelected) {
                                    viewModel.setGroupAsUnselected(currentGroup)
                                }else{
                                    viewModel.setGroupAsSelected(currentGroup)
                                }
                            },
                            isSelected = isSelected
                        )
                    }
                }
            }
        }
    }
}