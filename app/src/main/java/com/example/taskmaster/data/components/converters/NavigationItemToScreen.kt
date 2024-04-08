package com.example.taskmaster.data.components.converters

import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.models.navigation.Screen

fun convertNavigationItemToScreen(navigationItem: NavigationItem): Screen {
    return when (navigationItem) {
        is NavigationItem.TaskScreen -> Screen.TaskScreen
        is NavigationItem.FinishedScreen -> Screen.FinishedScreen
        is NavigationItem.GroupScreen -> Screen.GroupsScreen
        is NavigationItem.CreateTaskScreen -> Screen.CreateTaskScreen
    }
}