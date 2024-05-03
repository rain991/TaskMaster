package com.example.taskmaster.domain.repositories.core.student

import com.example.taskmaster.data.models.entities.Task
import kotlinx.coroutines.flow.Flow

interface StudentTaskListRepository {
    suspend fun getStudentTasks(studentGroupIdentifiers: List<String>): Flow<List<Task>>
}