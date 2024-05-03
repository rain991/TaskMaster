package com.example.taskmaster.data.viewModels.auth

import androidx.lifecycle.ViewModel
import com.example.taskmaster.R
import com.example.taskmaster.data.components.validateEmail
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.Teacher
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.example.taskmaster.presentation.UiText.UiText
import com.example.taskmaster.presentation.states.auth.RegisterScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _registerScreenState = MutableStateFlow(
        RegisterScreenState(
            userType = UserTypes.Student,
            name = "",
            surname = "",
            email = "",
            password = "",
            repeatPassword = ""
        )
    )
    val registerScreenState = _registerScreenState.asStateFlow()

    fun tryRegisterNewUser() {
        if (_registerScreenState.value.name.length < 2 || _registerScreenState.value.surname.length < 2) {
            setWarningMessage(UiText(R.string.register_name_length_error))
            return
        }
        if (!validateEmail(_registerScreenState.value.email)) {
            setWarningMessage(UiText(R.string.register_prohibited_characters_error))
            return
        }
        if (_registerScreenState.value.password != _registerScreenState.value.repeatPassword) {
            setWarningMessage(UiText(R.string.register_repeat_password_does_not_match_error))
            return
        }
        if (_registerScreenState.value.password.length < 6 && _registerScreenState.value.repeatPassword.length < 6) {
            setWarningMessage(UiText(R.string.register_password_length_should_be_at_least_error))
            return
        }

        if (_registerScreenState.value.userType == UserTypes.Student) {
            registerUseCase(
                user = Student(
                    userType = UserTypes.Student.name,
                    email = _registerScreenState.value.email,
                    password = _registerScreenState.value.password,
                    name = _registerScreenState.value.name,
                    surname = _registerScreenState.value.surname,
                    uid = "",
                    groups = listOf(),
                    tasks = listOf()
                )
            )
        }
        if (_registerScreenState.value.userType == UserTypes.Teacher) {
            registerUseCase(
                user = Teacher(
                    userType = UserTypes.Teacher.name,
                    email = _registerScreenState.value.email,
                    password = _registerScreenState.value.password,
                    name = _registerScreenState.value.name,
                    surname = _registerScreenState.value.surname,
                    groups = listOf(),
                    tasks = listOf()
                )
            )
        }
    }

    fun setUserType(value: UserTypes) {
        _registerScreenState.update { registerScreenState.value.copy(userType = value) }
    }

    fun setName(value: String) {
        _registerScreenState.value = registerScreenState.value.copy(name = value)
    }

    fun setSurname(value: String) {
        _registerScreenState.value = registerScreenState.value.copy(surname = value)
    }

    fun setEmail(value: String) {
        _registerScreenState.value = registerScreenState.value.copy(email = value)
    }

    fun setPassword(value: String) {
        _registerScreenState.value = registerScreenState.value.copy(password = value)
    }

    fun setRepeatPassword(value: String) {
        _registerScreenState.value = registerScreenState.value.copy(repeatPassword = value)
    }

    fun deleteWarningMessage() {
        _registerScreenState.value = registerScreenState.value.copy(warningMessage = null)
    }

    private fun setWarningMessage(value: UiText?) {
        _registerScreenState.value = registerScreenState.value.copy(warningMessage = value)
    }
}