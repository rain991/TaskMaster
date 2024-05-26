package com.example.taskmaster.data.implementations.auth

import android.util.Log
import androidx.navigation.NavController
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.constants.USER_DEFAULT_DESTINATION
import com.example.taskmaster.data.constants.USER_USERTYPE_FIELD
import com.example.taskmaster.domain.repositories.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val database: FirebaseFirestore, private val auth: FirebaseAuth) : LoginRepository {
    override suspend fun loginUser(email: String, password: String, navController: NavController) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(COMMON_DEBUG_TAG, "LoginRepositoryImpl : $email successfully login")
            } else {
                Log.d(COMMON_DEBUG_TAG, "User : $email incorrect login")
            }
        }
    }

    override fun checkIfUserLogined(auth: FirebaseAuth): Boolean {
        return auth.currentUser != null
    }

    private var userTypeReinstantiationCounter = 0
    override suspend fun getCurrentUserType(): String? {
        
        if (auth.currentUser == null && userTypeReinstantiationCounter < 10) {
            delay(200)
            userTypeReinstantiationCounter
            getCurrentUserType()
            return null
        }
        val currentUser = auth.currentUser ?: return null
        val uid = currentUser.uid
        val userCollection = database.collection(USER_DEFAULT_DESTINATION)
        return try {
            val querySnapshot = userCollection.whereEqualTo("uid", uid).get().await()
            val userDocuments = querySnapshot.documents
            if (userDocuments.isNotEmpty()) {
                val userType = userDocuments[0].getString(USER_USERTYPE_FIELD)
                userType
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
