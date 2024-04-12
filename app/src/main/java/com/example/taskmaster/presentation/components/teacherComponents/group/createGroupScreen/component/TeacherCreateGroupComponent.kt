package com.example.taskmaster.presentation.components.teacherComponents.group.createGroupScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.viewModels.teacher.GroupListScreenViewModel
import com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent.SingleGroupComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun TeacherCreateGroupComponent() {
    val lazyListState = rememberLazyListState()
    val viewModel = koinViewModel<GroupListScreenViewModel>()
    val groupList = viewModel.groupsList
    if (groupList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "You don't have group yet.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Easily create new one using button below")
            }
        }
    } else {
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxSize()) {
            items(groupList) {
                SingleGroupComponent(group = it) {
                    viewModel.setIsDeleteDialogShown(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}