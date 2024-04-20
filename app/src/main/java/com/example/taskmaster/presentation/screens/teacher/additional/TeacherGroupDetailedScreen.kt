package com.example.taskmaster.presentation.screens.teacher.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.data.components.converters.convertScreenToNavigationItem
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterBottomBar
import com.example.taskmaster.presentation.components.teacherComponents.group.groupDetailedScreen.component.GroupDetailedScreenComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun TeacherGroupDetailedScreen(navController: NavController, viewModel : GroupDetailedScreenViewModel) {
    // val auth = koinInject<FirebaseAuth>()
    val bottomBarNavigationItems =
        listOf(NavigationItem.TaskScreen, NavigationItem.FinishedScreen, NavigationItem.GroupScreen, NavigationItem.CreateTaskScreen)
   // val currentUserName = auth.currentUser?.displayName
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val screenManagerState = screenManagerViewModel.currentScreenState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
          //  SimplifiedTopBar()
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
            GroupDetailedScreenComponent(viewModel)
        }
    }
}