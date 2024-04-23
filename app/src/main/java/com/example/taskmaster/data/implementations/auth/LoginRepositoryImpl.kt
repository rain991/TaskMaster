package com.example.taskmaster.data.implementations.auth

import android.util.Log
import androidx.navigation.NavController
import com.example.taskmaster.data.constants.AUTH_DEBUG_TAG
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.domain.repositories.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val database: FirebaseFirestore, private val auth: FirebaseAuth) : LoginRepository {
    override suspend fun loginUser(email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(AUTH_DEBUG_TAG, "User : $email successfully login")
                navController.navigate(Screen.TaskScreen.route)
            } else {
                Log.d(AUTH_DEBUG_TAG, "User : $email incorrect login")
            }
        }
    }

    override fun checkIfUserLogined(auth: FirebaseAuth): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getCurrentUserType(): String? {
        val currentUser = auth.currentUser ?: return null
        val uid = currentUser.uid
        val userCollection = database.collection("users")
        val querySnapshot = userCollection.whereEqualTo("uid", uid).get().await()
        val userDocuments = querySnapshot.documents
        return if (userDocuments.isNotEmpty()) {
            val userType = userDocuments[0].getString("userType")
            userType
        } else {
            null
        }
    }
}
//return try {
//    val snapshot = database.collection("users").document(currentUser.uid).get().await()
//    snapshot.get(User::class.java)
//} catch (e: Exception) {
//    Log.e(AUTH_DEBUG_TAG, "Error getting current user", e)
//    null
//}