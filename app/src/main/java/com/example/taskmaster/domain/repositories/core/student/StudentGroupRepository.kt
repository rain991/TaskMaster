package com.example.taskmaster.domain.repositories.core.student

interface StudentGroupRepository {
    suspend fun addToGroupByIdentifier(studentUid: String, groupIdentifier: String)
}