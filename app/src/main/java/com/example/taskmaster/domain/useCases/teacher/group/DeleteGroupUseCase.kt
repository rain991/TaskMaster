package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.GroupRepositoryImpl

class DeleteGroupUseCase(private val groupRepositoryImpl: GroupRepositoryImpl) {
    operator fun invoke(groupIdentifier: String) {
        groupRepositoryImpl.deleteGroup(groupIdentifier)
    }
}