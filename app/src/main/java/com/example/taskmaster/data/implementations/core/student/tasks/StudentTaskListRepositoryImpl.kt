package com.example.taskmaster.data.implementations.core.student.tasks

import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.student.StudentTaskListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class StudentTaskListRepositoryImpl(
    private val database: FirebaseFirestore,
    private val listenersManagerViewModel: ListenersManagerViewModel
) : StudentTaskListRepository {
    override suspend fun getStudentTasks(studentGroupIdentifiers: List<String>): Flow<List<Task>> = callbackFlow {
        val tasksCollection = database.collection("tasks")
        val listener = tasksCollection
            .whereArrayContainsAny("groups", studentGroupIdentifiers)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val tasks = querySnapshot.documents.mapNotNull { document ->
                            document.toObject<Task>()
                        }
                        trySend(tasks).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }

}