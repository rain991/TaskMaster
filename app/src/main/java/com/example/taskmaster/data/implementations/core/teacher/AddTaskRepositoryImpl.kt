package com.example.taskmaster.data.implementations.core.teacher

import android.util.Log
import com.example.taskmaster.data.constants.AUTH_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.domain.repositories.core.teacher.AddTaskRepository
import com.google.firebase.firestore.FirebaseFirestore

class AddTaskRepositoryImpl(private val database: FirebaseFirestore) : AddTaskRepository {
    override fun addTask(task: Task) {
        val tasksReference = database.collection("tasks")
        tasksReference.add(task).addOnCompleteListener { Log.d(AUTH_DEBUG_TAG, "Successful adding new task") }
            .addOnFailureListener { exception ->
                Log.d(AUTH_DEBUG_TAG, "Error adding new task: ", exception)
            }
    }
}
