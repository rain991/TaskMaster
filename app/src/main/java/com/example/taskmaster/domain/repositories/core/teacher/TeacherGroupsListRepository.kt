package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Group
import kotlinx.coroutines.flow.Flow

interface TeacherGroupsListRepository{
    suspend fun getTeacherGroups(teacherUID : String) : Flow<List<Group>>
    suspend fun getTeacherGroup(teacherUID: String, groupIdentifier : String) : Flow<Group?>

    suspend fun getGroupsRelatedToTeacher(teacherUID : String) : List<Group>
}