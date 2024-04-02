package com.example.taskmaster.domain.repositories.login

import com.example.taskmaster.data.models.abstractionLayer.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface RegisterRepository {
    fun registerUser(user : User, auth: FirebaseAuth, database: FirebaseDatabase): Boolean
}