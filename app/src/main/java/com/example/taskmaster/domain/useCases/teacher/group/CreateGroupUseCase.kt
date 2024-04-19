package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.groups.GroupRepositoryImpl
import com.example.taskmaster.data.models.entities.Group

class CreateGroupUseCase(private val groupRepositoryImpl: GroupRepositoryImpl) {
    operator fun invoke(group : Group) {
        groupRepositoryImpl.createGroup(group)
    }
}