package com.example.taskmaster.domain.repositories.login

import com.example.taskmaster.data.models.abstractionLayer.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

interface LoginRepository {
fun checkIfUserLogined(auth : FirebaseAuth) : Boolean

fun getCurrentUser(auth: FirebaseAuth, database : FirebaseDatabase) : User  // used when user is logined, so it can't return nullable
}