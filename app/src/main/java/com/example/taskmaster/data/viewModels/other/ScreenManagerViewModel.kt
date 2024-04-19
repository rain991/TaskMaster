package com.example.taskmaster.data.viewModels.other

import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScreenManagerViewModel : ViewModel() {
    private val teacherScreenOptions = listOf(Screen.TaskScreen, Screen.FinishedScreen, Screen.GroupsScreen, Screen.CreateTaskScreen)
    private val studentScreenOptions = listOf(Screen.TaskScreen, Screen.FinishedScreen, Screen.GroupsScreen)


    private val _currentScreenState = MutableStateFlow<Screen>(Screen.TaskScreen)
    val currentScreenState = _currentScreenState.asStateFlow()

    fun setScreen(currentUserType: UserTypes, screen: Screen) {
        when (currentUserType) {
            is UserTypes.Teacher -> _currentScreenState.update { screen }
            is UserTypes.Student -> if (screen != Screen.CreateTaskScreen) {
                _currentScreenState.update { screen }
            }
        }
    }
}