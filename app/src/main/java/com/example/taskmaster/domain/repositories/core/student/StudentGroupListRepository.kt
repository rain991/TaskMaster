package com.example.taskmaster.domain.repositories.core.student

import com.example.taskmaster.data.models.entities.Group
import kotlinx.coroutines.flow.Flow

interface StudentGroupListRepository {
    suspend fun getStudentGroups(teacherUID : String) : Flow<List<Group>>
}