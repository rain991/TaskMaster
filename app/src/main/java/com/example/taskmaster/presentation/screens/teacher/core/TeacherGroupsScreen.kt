package com.example.taskmaster.presentation.screens.teacher.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.data.components.converters.convertScreenToNavigationItem
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.presentation.components.common.barsAndHeaders.SimplifiedTopBar
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterBottomBar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun TeacherGroupsScreen(navController: NavController) {
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val screenManagerState = screenManagerViewModel.currentScreenState.collectAsState()
    val auth = koinInject<FirebaseAuth>()
    val currentUserName = auth.currentUser?.displayName
    val bottomBarNavigationItems =
        listOf(NavigationItem.TaskScreen, NavigationItem.FinishedScreen, NavigationItem.GroupScreen, NavigationItem.CreateTaskScreen)
    val groupsLazyListState = rememberLazyListState()

    LaunchedEffect(key1 = Unit) {
        screenManagerViewModel.setScreen(UserTypes.Teacher, Screen.GroupsScreen)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SimplifiedTopBar()
        }, bottomBar = {
            TaskMasterBottomBar(
                items = bottomBarNavigationItems,
                selectedItem = convertScreenToNavigationItem(screenManagerState.value),
                navController = navController, userType = UserTypes.Teacher
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
               Text(text = "Create group", style = MaterialTheme.typography.titleMedium)
           }
            Spacer(modifier = Modifier.height(16.dp))




//            LazyColumn(state = groupsLazyListState, modifier = Modifier.fillMaxSize()) {
//                items(
//
//                ){
//
//                }
//            }
        }
    }
}