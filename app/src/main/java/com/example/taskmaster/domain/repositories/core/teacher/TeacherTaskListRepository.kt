package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Task
import kotlinx.coroutines.flow.Flow

interface TeacherTaskListRepository {
    suspend fun getTeacherTasks(teacherUID: String): Flow<List<Task>>
}