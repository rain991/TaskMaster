package com.example.taskmaster.data.implementations.core.student.tasks

import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.domain.repositories.core.student.StudentTaskRepository
import com.google.firebase.firestore.FirebaseFirestore

class StudentTaskRepositoryImpl(private val database: FirebaseFirestore) : StudentTaskRepository {
    override suspend fun addAnswerToTask(studentAnswer: StudentAnswer) {
        TODO("Not yet implemented")
    }

}