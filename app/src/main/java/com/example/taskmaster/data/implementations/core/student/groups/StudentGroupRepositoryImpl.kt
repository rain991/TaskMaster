package com.example.taskmaster.data.implementations.core.student.groups

import com.example.taskmaster.domain.repositories.core.student.StudentGroupRepository

class StudentGroupRepositoryImpl : StudentGroupRepository {
    override suspend fun addToGroupByIdentifier(studentUid: String, groupIdentifier: String) {
        TODO("Not yet implemented")
    }

}