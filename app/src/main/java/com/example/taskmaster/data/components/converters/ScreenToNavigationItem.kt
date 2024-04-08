package com.example.taskmaster.data.components.converters

import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.models.navigation.Screen

fun convertScreenToNavigationItem(screen: Screen): NavigationItem {
    return when (screen) {
        is Screen.TaskScreen -> NavigationItem.TaskScreen
        is Screen.FinishedScreen -> NavigationItem.FinishedScreen
        is Screen.GroupsScreen -> NavigationItem.GroupScreen
        is Screen.CreateTaskScreen -> NavigationItem.CreateTaskScreen
        is Screen.GroupDetailedScreen -> NavigationItem.GroupScreen
        else -> {
            NavigationItem.TaskScreen
        }

    }
}