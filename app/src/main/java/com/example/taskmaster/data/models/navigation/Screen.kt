package com.example.taskmaster.data.models.navigation

sealed class Screen(val route: String) {
    // Login & Register
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object ResetPasswordScreen : Screen("reset_password_screen")
    object ScreenPlaceholder : Screen("screen_placeholder")

    // Common
    object ProfileScreen : Screen("profile_screen")
    object TaskScreen : Screen("task_screen")
    object FinishedScreen : Screen("finished_screen")
    object GroupsScreen : Screen("group_screen")

    // Teacher related
    object CreateTaskScreen : Screen("create_task_screen")
    object GroupDetailedScreen : Screen("group_detailed_screen")
    object CreateGroupScreen : Screen("create_group_screen")
}