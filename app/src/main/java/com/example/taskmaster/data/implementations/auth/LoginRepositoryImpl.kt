package com.example.taskmaster.data.implementations.auth

import android.util.Log
import com.example.taskmaster.data.constants.AUTH_DEBUG_TAG
import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.domain.repositories.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl : LoginRepository {
    override fun checkIfUserLogined(auth: FirebaseAuth): Boolean {
        return auth.currentUser != null
    }
    override suspend fun getCurrentUser(auth: FirebaseAuth, database: FirebaseDatabase): User? {
        val currentUser = auth.currentUser ?: return null
        return try {
            val snapshot = database.getReference("users").child(currentUser.uid).get().await()
            snapshot.getValue(User::class.java)
        } catch (e: Exception) {
            Log.e(AUTH_DEBUG_TAG, "Error getting current user", e)
            null
        }
    }
}
