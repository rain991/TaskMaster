package com.example.taskmaster.data.implementations.auth

import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.domain.repositories.login.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterRepositoryImpl : RegisterRepository {
    override fun registerUser(user: User, auth: FirebaseAuth, database: FirebaseDatabase): Boolean {
       TODO()
    }
}