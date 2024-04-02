package com.example.taskmaster.domain.repositories.login

import com.google.firebase.auth.FirebaseAuth

interface LoginRepository {
fun checkIfUserLogined(auth : FirebaseAuth) : Boolean

fun getCurrentUser()
}