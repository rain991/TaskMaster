package com.example.taskmaster.data.implementations.core.student.groups

import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.student.StudentGroupListRepository
import kotlinx.coroutines.flow.Flow

class StudentGroupListRepositoryImpl : StudentGroupListRepository {
    override suspend fun getStudentGroups(teacherUID: String): Flow<List<Group>> {
        TODO("Not yet implemented")
    }

}