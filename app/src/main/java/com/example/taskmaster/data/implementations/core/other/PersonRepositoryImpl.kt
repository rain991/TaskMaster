package com.example.taskmaster.data.implementations.core.other

import android.util.Log
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.domain.repositories.core.other.PersonRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PersonRepositoryImpl(private val database: FirebaseFirestore) : PersonRepository {
    override suspend fun findPersonUIDByEmail(email: String): String {
        val usersRef = database.collection("users")

        return try {
            val querySnapshot = usersRef.whereEqualTo("email", email).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            userDoc?.getString("uid") ?: ""
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error fetching user UID by email", e)
            ""
        }
    }
}