package com.example.taskmaster.data.implementations.core.teacher

import android.util.Log
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.teacher.GroupsListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class GroupsListRepositoryImpl(private val database: FirebaseFirestore) : GroupsListRepository {
    override suspend fun getGroupsRelatedToTeacher(teacherUID: String): List<Group> {
        val groupCollection = database.collection("groups")
        return try {
            val querySnapshot = groupCollection.whereEqualTo("teacher", teacherUID).get().await()
            querySnapshot.documents.mapNotNull { document ->
                try {
                    document.toObject<Group>()
                } catch (e: Exception) {
                    Log.d(QUERY_DEBUG_TAG, "Error converting document to Group", e)
                    null
                }
            }
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error fetching groups for teacher", e)
            emptyList<Group>()
        }
    }
}