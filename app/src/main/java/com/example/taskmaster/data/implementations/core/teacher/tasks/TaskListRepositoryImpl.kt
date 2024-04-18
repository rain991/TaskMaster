package com.example.taskmaster.data.implementations.core.teacher.tasks

import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.domain.repositories.core.teacher.TaskListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TaskListRepositoryImpl(private val database : FirebaseFirestore) : TaskListRepository {

    override suspend fun getTeacherTasks(teacherUID: String): Flow<List<Task> > = callbackFlow {
        val tasksCollection = database.collection("tasks")
        val listener = tasksCollection
            .whereEqualTo("teacher", teacherUID)
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
        awaitClose { listener.remove() }
    }
}