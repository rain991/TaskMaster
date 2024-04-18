package com.example.taskmaster.domain.repositories.core.teacher

interface TaskRepository {
    suspend fun getTeacherNameByUid(teacherUid: String): String
    suspend fun getGroupNameByIdentifier(groupIdentifier: String): String
}