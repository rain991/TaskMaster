package com.example.taskmaster.presentation.screens.teacher.additional

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.components.converters.convertScreenToNavigationItem
import com.example.taskmaster.data.constants.TEACHER_BOTTOM_BAR_NAVIGATION_ITEMS
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherAnswerViewModel
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterBottomBar
import com.example.taskmaster.presentation.components.teacherComponents.answerDetailed.screenComponents.TeacherAnswerDetailedScreenComponent
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun TeacherAnswerDetailedScreen(navController: NavController, answerDetailedViewModel: TeacherAnswerViewModel) {
    val auth = koinInject<FirebaseAuth>()
    val currentUserName = auth.currentUser?.displayName
    val bottomBarNavigationItems = TEACHER_BOTTOM_BAR_NAVIGATION_ITEMS
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val screenManagerState = screenManagerViewModel.currentScreenState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        screenManagerViewModel.setScreen(UserTypes.Teacher, Screen.AnswerDetailedScreen)
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (currentUserName != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 4.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.answer_detailed_screen_go_back),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { navController.navigate(Screen.TaskDetailedScreen.route) })
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.appicon1),
                        contentDescription = stringResource(R.string.app_icon),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
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
            TeacherAnswerDetailedScreenComponent(answerDetailedViewModel)
        }
    }
}