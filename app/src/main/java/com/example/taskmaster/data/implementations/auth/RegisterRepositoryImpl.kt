package com.example.taskmaster.data.implementations.auth

import android.util.Log
import com.example.taskmaster.data.constants.AUTH_DEBUG_TAG
import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.domain.repositories.login.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class RegisterRepositoryImpl : RegisterRepository {
    override fun <T: User> registerUser(user: T, auth: FirebaseAuth, database: FirebaseDatabase) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { authenification ->
                if (authenification.isSuccessful) {
                    val current_user = FirebaseAuth.getInstance().currentUser
                    current_user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName("${user.name} ${user.surname}").build()
                        current_user.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(AUTH_DEBUG_TAG, "User profile updated")
                                }
                            }
                    }
                    if (current_user != null) {
                        database.getReference("users").child(current_user.uid).setValue(user)
                    }
                } else {
                    Log.w(AUTH_DEBUG_TAG, "createUserWithEmail : failure", authenification.exception)
                }
            }
    }
}