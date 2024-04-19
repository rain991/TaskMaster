package com.example.taskmaster.data.implementations.core.teacher.tasks

import android.util.Log
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.domain.repositories.core.teacher.TaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TaskRepositoryImpl(private val database: FirebaseFirestore) : TaskRepository {
    override suspend fun getTeacherNameByUid(teacherUid: String): String {
        val usersRef = database.collection("users")
        return try {
            val querySnapshot = usersRef.whereEqualTo("uid", teacherUid).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            (userDoc?.getString("name") + " " + userDoc?.getString("surname"))
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error getting teacher name by UID", e)
            ""
        }
    }

    override suspend fun getGroupNameByIdentifier(groupIdentifier: String): String {
        val groupsRef = database.collection("groups")
        return try {
            val querySnapshot = groupsRef.whereEqualTo("identifier", groupIdentifier).get().await()
            val userDoc = querySnapshot.documents.firstOrNull()
            userDoc?.getString("name") ?: ""
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error getting group name by identifier", e)
            ""
        }
    }
}