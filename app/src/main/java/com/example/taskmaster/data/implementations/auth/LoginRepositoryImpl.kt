package com.example.taskmaster.data.implementations.auth

import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.domain.repositories.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginRepositoryImpl : LoginRepository {
    override fun checkIfUserLogined(auth: FirebaseAuth): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(auth: FirebaseAuth, database: FirebaseDatabase): User {
        TODO("Not yet implemented")
    }
}