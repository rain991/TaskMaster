package com.example.taskmaster.data.implementations.core.student.groups

import com.example.taskmaster.data.constants.GROUPS_COLLECTION
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.student.StudentGroupListRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class StudentGroupListRepositoryImpl(private val database: FirebaseFirestore, private val listenersManagerViewModel: ListenersManagerViewModel) : StudentGroupListRepository {
    override suspend fun getStudentGroups(studentEmail : String) : Flow<List<Group>> = callbackFlow {
        val groupCollection = database.collection(GROUPS_COLLECTION)
        val listener = groupCollection
            .whereArrayContains("students", studentEmail)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val groups = querySnapshot.documents.mapNotNull { document ->
                            val group = document.toObject(Group::class.java)
                            group?.let {
                                it.copy(students = it.students ?: listOf())
                            }
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
}