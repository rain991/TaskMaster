package com.example.taskmaster.domain.useCases.common

import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.models.abstractionLayer.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterUseCase(private val registerRepositoryImpl: RegisterRepositoryImpl) {
    operator fun invoke(user: User, auth: FirebaseAuth, database: FirebaseDatabase) {
        registerRepositoryImpl.registerUser(user = user, auth = auth, database = database)
    }
}