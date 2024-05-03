package com.example.taskmaster.domain.useCases.common

import androidx.navigation.NavController
import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

class LoginUseCase(private val loginRepositoryImpl: LoginRepositoryImpl, private val firebaseAuth: FirebaseAuth) {
    suspend operator fun invoke(email : String, password : String, navController: NavController) {
        loginRepositoryImpl.loginUser(email  = email, password = password, navController = navController)
    }
}