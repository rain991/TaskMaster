package com.example.taskmaster.data.models.navigation

import com.example.taskmaster.R

sealed class NavigationItem(var route: String, val icon: Int, var titleResId: Int) { // Used in bottomAppBar
    object TaskScreen : NavigationItem(
        Screen.TaskScreen.route,
        R.drawable.baseline_checklist_rtl_24,
        R.string.tasks,
    )

    object FinishedScreen : NavigationItem(
        Screen.FinishedScreen.route,
        R.drawable.baseline_task_alt_24,
        R.string.finished
    )

    object GroupScreen : NavigationItem(
        Screen.GroupsScreen.route,
        R.drawable.baseline_group_24,
        R.string.groups
    )

    object CreateTaskScreen : NavigationItem(
        Screen.CreateTaskScreen.route,
        R.drawable.baseline_create_24,
        R.string.create_task
    )
}