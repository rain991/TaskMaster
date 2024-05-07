package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Group

interface TeacherGroupRepository {
    suspend fun createGroup(group: Group)
    suspend fun deleteGroup(groupIdentifier: String)
    suspend fun deleteStudentFromGroup(studentUID: String, groupIdentifier: String)
    suspend fun setAppliableGroupState(state : Boolean, groupIdentifier: String)
    suspend fun getCurrentAppliableState(groupIdentifier: String): Boolean
}