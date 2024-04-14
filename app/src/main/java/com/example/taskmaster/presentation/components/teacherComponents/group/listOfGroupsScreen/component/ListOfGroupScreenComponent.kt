package com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupListScreenViewModel
import com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent.SingleGroupComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListOfGroupScreenComponent(navController: NavController, groupDetailedViewModel :GroupDetailedScreenViewModel) {
   // val groupDetailedViewModel = koinViewModel<GroupDetailedScreenViewModel>()
    val viewModel = koinViewModel<GroupListScreenViewModel>()
    val lazyListState = rememberLazyListState()
    val groupList = viewModel.groupsList
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCurrentGroups()
    }
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
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Groups", style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold))
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(state = lazyListState, modifier = Modifier.fillMaxSize()) {
                items(groupList) {
                    SingleGroupComponent(group = it, onComponentClick = {
                        groupDetailedViewModel.setCurrentDetailedGroup(it)
                        navController.navigate(Screen.GroupDetailedScreen.route)
                    }) {
                        viewModel.setIsDeleteDialogShown(it)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}