package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Group
import kotlinx.coroutines.flow.Flow

interface GroupsListRepository{
    suspend fun getGroupsRelatedToTeacher(teacherUID : String) : Flow<List<Group>>
}