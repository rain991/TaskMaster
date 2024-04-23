package com.example.taskmaster.domain.repositories.login

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

interface LoginRepository {
    suspend fun loginUser(email : String, password : String, navController: NavController)

    fun checkIfUserLogined(auth: FirebaseAuth): Boolean

    suspend fun getCurrentUserType(): String?
}