package com.example.taskmaster.data.constants

import com.example.taskmaster.data.models.navigation.NavigationItem

// Debug
const val AUTH_DEBUG_TAG = "Auth"
const val COMMON_DEBUG_TAG = "TM_CommonTag"
const val SEARCH_DEBUG_TAG = "TM_SearchTag"
const val QUERY_DEBUG_TAG = "TM_FirebaseQueryTag"

// Common
const val DEFAULT_USER_NAME = "user"
val TEACHER_BOTTOM_BAR_NAVIGATION_ITEMS =
    listOf(NavigationItem.TaskScreen, NavigationItem.FinishedScreen, NavigationItem.GroupScreen, NavigationItem.CreateTaskScreen)

//Files
const val MAX_FILE_SIZE_BYTES = 4 * 1024 * 1024 // 4 MB in bytes
const val MAX_FILES_TO_SELECT = 5

// Delays
const val FINISHED_TASKS_DATA_REQUEST_TIME = 500L



