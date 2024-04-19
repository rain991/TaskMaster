package com.example.taskmaster.presentation.states.auth

data class LoginScreenState(
    val email : String,
    val password : String,
    val warningMessage : String? = null
)
