package com.example.taskmaster.data.implementations.core.teacher.groups

import android.util.Log
import com.example.taskmaster.data.constants.GROUPS_COLLECTION
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.teacher.TeacherGroupsListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TeacherGroupsListRepositoryImpl(private val database: FirebaseFirestore, private val listenersManagerViewModel: ListenersManagerViewModel) :
    TeacherGroupsListRepository {
    override suspend fun getGroupsRelatedToTeacher(teacherUID: String): List<Group> {
        val groupCollection = database.collection(GROUPS_COLLECTION)
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


    override suspend fun getTeacherGroups(teacherUID: String): Flow<List<Group>> = callbackFlow {
        val groupCollection = database.collection(GROUPS_COLLECTION)
        val listener = groupCollection
            .whereEqualTo("teacher", teacherUID)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val groups = querySnapshot.documents.mapNotNull { document ->
                            document.toObject<Group>()
                        }
                        trySend(groups).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }

    override suspend fun getTeacherGroup(teacherUID: String, groupIdentifier: String): Flow<Group?> = callbackFlow {
        val groupCollection = database.collection(GROUPS_COLLECTION)
        val listener = groupCollection
            .whereEqualTo("teacher", teacherUID)
            .whereEqualTo("identifier", groupIdentifier)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val group = querySnapshot.documents.firstOrNull()?.toObject<Group>()
                        trySend(group).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }

}