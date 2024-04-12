package com.example.taskmaster.data.implementations.core.teacher

import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.teacher.GroupsListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GroupsListRepositoryImpl(private val database : FirebaseFirestore) : GroupsListRepository {

    override suspend fun getGroupsRelatedToTeacher(teacherUID: String): Flow<List<Group>> = callbackFlow {
        val groupCollection = FirebaseFirestore.getInstance().collection("groups")
        val listener = groupCollection.whereEqualTo("uid", teacherUID)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    trySend(querySnapshot.documents.mapNotNull { document ->
                        try {
                            document.toObject<Group>()
                        } catch (e: Exception) {
                            null
                        }
                    }).isSuccess
                }
            }

        awaitClose { listener.remove() }
    }
}