package com.example.taskmaster.domain.repositories.core.student

interface StudentGroupRepository {
    suspend fun addToGroupByIdentifier(studentEmail: String, groupIdentifier: String)
}