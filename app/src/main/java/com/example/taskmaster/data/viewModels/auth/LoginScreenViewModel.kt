package com.example.taskmaster.data.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.components.validateEmail
import com.example.taskmaster.domain.useCases.common.LoginUseCase
import com.example.taskmaster.presentation.UiText.UiText
import com.example.taskmaster.presentation.states.auth.LoginScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(
        LoginScreenState(
            email = "",
            password = ""
        )
    )
    val screenState = _screenState.asStateFlow()

    suspend fun login(navController: NavController) {
        if (!validateEmail(_screenState.value.email)) {
            setWarningMessage(UiText(R.string.login_incorrect_email))
            return
        }
        if (_screenState.value.password.length < 6) {
            setWarningMessage(UiText(R.string.login_incorrect_password))
            return
        }
        loginUseCase(email = _screenState.value.email, password = _screenState.value.password, navController = navController)
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

    private fun setWarningMessage(value: UiText?) {
        _screenState.value = screenState.value.copy(warningMessage = value)
    }
}