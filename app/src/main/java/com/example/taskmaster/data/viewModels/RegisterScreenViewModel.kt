package com.example.taskmaster.data.viewModels

import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.Teacher
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.example.taskmaster.presentation.states.RegisterScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.regex.Pattern

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
            setWarningMessage("Your name or surname should be at least 2 characters")
            return
        }
        if (!validateEmail(_registerScreenState.value.email)) {
            setWarningMessage("Email shouldn't contain prohibited characters")
            return
        }
        if (_registerScreenState.value.password != _registerScreenState.value.repeatPassword) {
            setWarningMessage("Repeat password does not match")
            return
        }
        if (_registerScreenState.value.password.length<6 && _registerScreenState.value.repeatPassword.length<6) {
            setWarningMessage("Password length should be at least 6")
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
                    groups = listOf(),
                    tasks = listOf()
                )
            )
        }
        if (_registerScreenState.value.userType == UserTypes.Teacher) {
            registerUseCase(
                user = Teacher(
                    userType = UserTypes.Student.name,
                    email = _registerScreenState.value.email,
                    password = _registerScreenState.value.password,
                    name = _registerScreenState.value.name,
                    surname = _registerScreenState.value.surname,
                    classes = listOf()
                )
            )
        }
    }
    fun setUserType(value: UserTypes) {
        _registerScreenState.value = registerScreenState.value.copy(userType = value)
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

    fun deleteWarningMessage(){
        _registerScreenState.value = registerScreenState.value.copy(warningMessage = null)
    }

    private fun setWarningMessage(value: String?) {
        _registerScreenState.value = registerScreenState.value.copy(warningMessage = value)
    }

    private fun validateEmail(email: String): Boolean {
        val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}
