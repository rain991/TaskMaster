package com.example.taskmaster.data.models.navigation

sealed class Screen(val route: String) {
    //common
    object TaskScreen : Screen("task_screen")
    object FinishedScreen : Screen("finished_screen")
    object GroupsScreen : Screen("group_screen")
    // teacher related
    object CreateTaskScreen : Screen("create_task_screen")
}