package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Group

interface GroupsListRepository{
    suspend fun getGroupsRelatedToTeacher(teacherUID : String) : List<Group>
}