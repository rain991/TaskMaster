package com.example.taskmaster.data.implementations.auth

import android.util.Log
import com.example.taskmaster.data.constants.AUTH_DEBUG_TAG
import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.domain.repositories.login.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterRepositoryImpl(private val auth: FirebaseAuth, private val database: FirebaseFirestore) : RegisterRepository {
    override fun <T : User> registerUser(user: T) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { authenification ->
                if (authenification.isSuccessful) {
                    val current_auth_user = auth.currentUser
                    val user_data: MutableMap<String, Any?> = mutableMapOf(
                        "uid" to current_auth_user?.uid,
                        "name" to user.name,
                        "surname" to user.surname,
                        "email" to user.email,
                        "userType" to user.userType,
                        "groups" to listOf<String>(),
                        "tasks" to listOf<String>()

                    )
                    if (current_auth_user != null) {
                        database.collection("users").add(user_data).addOnCompleteListener {
                            Log.d(AUTH_DEBUG_TAG, "Successful writing user to database")
                        }.addOnCanceledListener {
                            Log.d(AUTH_DEBUG_TAG, "Writing user is cancelled to database")
                        }.addOnFailureListener { exception ->
                            Log.d(AUTH_DEBUG_TAG, "Error writing user to database", exception)
                        }
                    } else {
                        Log.d(AUTH_DEBUG_TAG, "Current user UID is null")
                    }
                    current_auth_user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName("${user.name} ${user.surname}").build()
                        current_auth_user.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(AUTH_DEBUG_TAG, "${current_auth_user.uid} : user profile updated")
                                }
                            }
                    }
                } else {
                    Log.w(AUTH_DEBUG_TAG, "createUserWithEmail : failure", authenification.exception)
                }
            }
    }
}