package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Group

interface GroupRepository {
fun createGroup(group : Group)
fun deleteGroup(groupIdentifier : String)
}