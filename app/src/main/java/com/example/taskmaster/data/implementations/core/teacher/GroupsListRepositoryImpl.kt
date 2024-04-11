package com.example.taskmaster.data.implementations.core.teacher

import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.teacher.GroupsListRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GroupsListRepositoryImpl(private val database : FirebaseFirestore) : GroupsListRepository {
    override suspend fun getGroupsRelatedToTeacher(teacherUID: String): List<Group> {
        val groupCollection = database.collection("groups")
        val querySnapshot = groupCollection.whereEqualTo("uid", teacherUID).get().await()
        val groupDocuments = querySnapshot.documents
        return groupDocuments.mapNotNull { document ->
            try {
                document.toObject(Group::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}