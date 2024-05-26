package com.example.taskmaster.data.implementations.core.other

import android.util.Log
import com.example.taskmaster.data.constants.ANSWERS_COLLECTION
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.Teacher
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.domain.repositories.core.other.PersonRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PersonRepositoryImpl(private val database: FirebaseFirestore) : PersonRepository {
    override suspend fun findPersonUIDByEmail(email: String): String {
        val usersRef = database.collection(ANSWERS_COLLECTION)
        return try {
            val querySnapshot = usersRef.whereEqualTo("email", email).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            userDoc?.getString("uid") ?: ""
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error fetching user UID by email", e)
            ""
        }
    }

    override suspend fun getCurrentUserType(uid: String): String {
        val usersRef = database.collection(ANSWERS_COLLECTION)
        return try {
            val querySnapshot = usersRef.whereEqualTo("uid", uid).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            userDoc?.getString("userType") ?: ""
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error getting user type by UID", e)
            ""
        }
    }

    override suspend fun getCurrentUser(uid: String): User? {
        val usersRef = database.collection(ANSWERS_COLLECTION)
        val currentUserType = getCurrentUserType(uid)
        return try {
            val querySnapshot = usersRef.whereEqualTo("uid", uid).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            if (userDoc != null) {
                return when (currentUserType) {
                    UserTypes.Student.name -> {
                        userDoc.toObject(Student::class.java)
                    }

                    UserTypes.Teacher.name -> {
                        userDoc.toObject(Teacher::class.java)
                    }
                    else -> {
                        null
                    }
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}