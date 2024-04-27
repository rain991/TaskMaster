package com.example.taskmaster.domain.repositories.core.student

import com.example.taskmaster.data.models.entities.StudentAnswer

interface StudentTaskRepository {
    suspend fun addAnswerToTask(studentAnswer: StudentAnswer)
}