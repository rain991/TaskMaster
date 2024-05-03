package com.example.taskmaster.presentation.states.auth

import com.example.taskmaster.presentation.UiText.UiText

data class LoginScreenState(
    val email : String,
    val password : String,
    val warningMessage : UiText? = null
)
