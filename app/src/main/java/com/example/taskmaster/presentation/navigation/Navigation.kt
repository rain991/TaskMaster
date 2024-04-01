package com.example.taskmaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.screens.common.LoginScreen
import com.example.taskmaster.presentation.screens.common.RegisterScreen
import com.example.taskmaster.presentation.screens.student.StudentFinishedTasksScreen
import com.example.taskmaster.presentation.screens.student.StudentGroupsScreen
import com.example.taskmaster.presentation.screens.student.StudentTasksScreen
import com.example.taskmaster.presentation.screens.teacher.additional.TeacherGroupDetailedScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherCreateTaskScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherFinishedTasksScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherGroupsScreen
import com.example.taskmaster.presentation.screens.teacher.core.TeacherTasksScreen


@Composable
fun Navigation(currentUserType: UserTypes) {
    val navController = rememberNavController()
//    LaunchedEffect(key1 = loginCount) {
//        if (loginCount.value == 0) {
//            navController.navigate(Screen.LoginScreen.route)
//        } else {
//            navController.navigate(Screen.MainScreen.route)
//        }
//    }
    NavHost(
        navController = navController,
        startDestination = Screen.RegisterScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen()
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterScreen()
        }
        composable(route = Screen.TaskScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentTasksScreen()
            } else {
                TeacherTasksScreen()
            }
        }
        composable(route = Screen.FinishedScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentFinishedTasksScreen()
            } else {
                TeacherFinishedTasksScreen()
            }
        }
        composable(route = Screen.GroupsScreen.route) {
            if (currentUserType == UserTypes.Student) {
                StudentGroupsScreen()
            } else {
                TeacherGroupsScreen()
            }
        }
        composable(route = Screen.CreateTaskScreen.route) {
            TeacherCreateTaskScreen()
        }
        composable(route = Screen.GroupDetailedScreen.route) {
            TeacherGroupDetailedScreen()
        }
    }
}