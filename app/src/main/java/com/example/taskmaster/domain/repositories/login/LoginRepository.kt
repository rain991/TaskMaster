package com.example.taskmaster.domain.repositories.login

import com.google.firebase.auth.FirebaseAuth

interface LoginRepository {
    suspend fun loginUser(email : String, password : String)

    fun checkIfUserLogined(auth: FirebaseAuth): Boolean

    suspend fun getCurrentUserType(): String?
}