package com.example.taskmaster.data.viewModels.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.other.PersonRepositoryImpl
import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenManagerViewModel(private val auth: FirebaseAuth, private val personRepositoryImpl: PersonRepositoryImpl) : ViewModel() {
    private val teacherScreenOptions = listOf(Screen.TaskScreen, Screen.FinishedScreen, Screen.GroupsScreen, Screen.CreateTaskScreen)
    private val studentScreenOptions = listOf(Screen.TaskScreen, Screen.FinishedScreen, Screen.GroupsScreen)

    private val _currentScreenState = MutableStateFlow<Screen>(Screen.TaskScreen)
    val currentScreenState = _currentScreenState.asStateFlow()

    private val currentUid = auth.currentUser?.uid


    private val _currentUserState = MutableStateFlow<User?>(null)
    val currentUserType = _currentUserState.asStateFlow()

    init {
        viewModelScope.launch {
            if (currentUid != null) {
                setCurrentUser(personRepositoryImpl.getCurrentUser(currentUid))
            }
        }
    }

    fun setScreen(currentUserType: UserTypes, screen: Screen) {
        when (currentUserType) {
            is UserTypes.Teacher -> _currentScreenState.update { screen }
            is UserTypes.Student -> if (screen != Screen.CreateTaskScreen) {
                _currentScreenState.update { screen }
            }
        }
    }

    private fun setCurrentUser(user: User?) {
        _currentUserState.value = user
    }
}