package com.example.taskmaster.presentation.states

data class LoginScreenState(
    val email : String,
    val password : String,
    val warningMessage : String? = null
)
