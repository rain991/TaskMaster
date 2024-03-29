package com.example.taskmaster.presentation.states

import com.example.taskmaster.data.models.entities.UserTypes

data class RegisterScreenState(
    val userType : UserTypes,
    val name : String,
    val surname: String,
    val email : String,
    val password : String,
    val repeatPassword: String
)
