package com.example.taskmaster.data.viewModels.auth

import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.validateEmail
import com.example.taskmaster.domain.useCases.common.LoginUseCase
import com.example.taskmaster.presentation.states.auth.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) : ViewModel() {
    private val _screenState = MutableStateFlow(
        LoginScreenState(
            email = "",
            password = ""
        )
    )
    val screenState = _screenState.asStateFlow()

    suspend fun login() {
        if (!validateEmail(_screenState.value.email)) {
            setWarningMessage("Incorrect email")
        }
        if (_screenState.value.password.length < 6) {
            setWarningMessage("Incorrect password")
        }
        loginUseCase(email = _screenState.value.email, password = _screenState.value.password)
        if (auth.currentUser == null) {
            setWarningMessage("Such user doesn't exist, or your password is incorrect")
        }
    }

    fun setEmail(value: String) {
        _screenState.value = screenState.value.copy(email = value)
    }

    fun setPassword(value: String) {
        _screenState.value = screenState.value.copy(password = value)
    }

    fun deleteWarningMessage() {
        _screenState.value = screenState.value.copy(warningMessage = null)
    }

    private fun setWarningMessage(value: String?) {
        _screenState.value = screenState.value.copy(warningMessage = value)
    }
}