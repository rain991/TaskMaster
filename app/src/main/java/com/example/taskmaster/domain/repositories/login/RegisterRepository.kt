package com.example.taskmaster.domain.repositories.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface RegisterRepository {
fun registerUser(auth: FirebaseAuth, database: FirebaseDatabase) : Boolean
}