package com.example.taskmaster.presentation.states.auth

import com.example.taskmaster.data.models.entities.UserTypes

data class RegisterScreenState(
    val userType : UserTypes,
    val name : String,
    val surname: String,
    val email : String,
    val password : String,
    val repeatPassword: String,
    val warningMessage : String? = null
)
