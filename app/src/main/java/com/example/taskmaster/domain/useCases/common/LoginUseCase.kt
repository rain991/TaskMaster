package com.example.taskmaster.domain.useCases.common

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.models.abstractionLayer.User
import com.google.firebase.auth.FirebaseAuth

class LoginUseCase(private val loginRepositoryImpl: LoginRepositoryImpl, private val firebaseAuth: FirebaseAuth) {

    suspend operator fun invoke(user: User) {
        loginRepositoryImpl.loginUser(user = user, auth = firebaseAuth)
    }
}