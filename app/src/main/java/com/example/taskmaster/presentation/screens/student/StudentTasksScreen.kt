package com.example.taskmaster.presentation.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.data.components.converters.convertScreenToNavigationItem
import com.example.taskmaster.data.constants.STUDENT_BOTTOM_BAR_NAVIGATION_ITEMS
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.student.answers.StudentAnswerScreenViewModel
import com.example.taskmaster.presentation.components.common.barsAndHeaders.SimplifiedTopBar
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterBottomBar
import com.example.taskmaster.presentation.components.studentComponents.task.unfinished.screenComponent.StudentTaskScreenComponent
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun StudentTasksScreen(navController: NavController, studentAnswerScreenViewModel : StudentAnswerScreenViewModel) {
    val auth = koinInject<FirebaseAuth>()
    val currentUserName = auth.currentUser?.displayName
    val bottomBarNavigationItems = STUDENT_BOTTOM_BAR_NAVIGATION_ITEMS
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val screenManagerState = screenManagerViewModel.currentScreenState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        screenManagerViewModel.setScreen(UserTypes.Student, Screen.TaskScreen)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentUserName != null) {
                SimplifiedTopBar(
                    onPersonIconClick = { navController.navigate(Screen.ProfileScreen.route) }
                )
            }
        }, bottomBar = {
            TaskMasterBottomBar(
                items = bottomBarNavigationItems,
                selectedItem = convertScreenToNavigationItem(screenManagerState.value),
                navController = navController, userType = UserTypes.Student
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
            StudentTaskScreenComponent(navController, studentAnswerScreenViewModel)
        }
    }
}