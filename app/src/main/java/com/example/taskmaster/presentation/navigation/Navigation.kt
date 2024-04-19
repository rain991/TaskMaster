package com.example.taskmaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import com.example.taskmaster.presentation.screens.common.LoginScreen
import com.example.taskmaster.presentation.screens.common.RegisterScreen
import com.example.taskmaster.presentation.screens.common.ResetPasswordScreen
import com.example.taskmaster.presentation.screens.common.ScreenPlaceholder
import com.example.taskmaster.presentation.screens.student.StudentFinishedTasksScreen
import com.example.taskmaster.presentation.screens.student.StudentGroupsScreen
import com.example.taskmaster.presentation.screens.student.StudentTasksScreen
import com.example.taskmaster.presentation.screens.teacher.additional.TeacherGroupCreateScreen
import com.example.taskmaster.presentation.screens.teacher.additional.TeacherGroupDetailedScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherCreateTaskScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherFinishedTasksScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherGroupsScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherTasksScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun Navigation(isLogined: Boolean, startDestination: String, currentUserType: UserTypes?) {
    val navController = rememberNavController()
    val groupDetailedViewModel = koinViewModel<GroupDetailedScreenViewModel>()
    NavHost(
        navController = navController,
        startDestination = startDestination  // startDestination
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }
        composable(route = Screen.ScreenPlaceholder.route) {
            ScreenPlaceholder()
        }
        composable(route = Screen.TaskScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentTasksScreen(navController)
            } else {
                TeacherTasksScreen(navController)
            }
        }
        composable(route = Screen.FinishedScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentFinishedTasksScreen(navController)
            } else {
                TeacherFinishedTasksScreen(navController)
            }
        }
        composable(route = Screen.CreateTaskScreen.route) {
            TeacherCreateTaskScreen(navController)
        }
        composable(route = Screen.GroupsScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentGroupsScreen(navController)
            } else {
                TeacherGroupsScreen(navController, groupDetailedViewModel)
            }
        }

        composable(route = Screen.CreateGroupScreen.route) {
            TeacherGroupCreateScreen(navController)
        }

        composable(route = Screen.GroupDetailedScreen.route) {
            TeacherGroupDetailedScreen(navController, groupDetailedViewModel)
        }
    }
}