package com.example.taskmaster.domain.useCases.common

import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.models.abstractionLayer.User

class RegisterUseCase(
    private val registerRepositoryImpl: RegisterRepositoryImpl
) {
    operator fun <T : User> invoke(user: T) {
        registerRepositoryImpl.registerUser(user = user)
    }
}