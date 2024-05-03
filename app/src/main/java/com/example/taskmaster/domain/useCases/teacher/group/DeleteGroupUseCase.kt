package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupRepositoryImpl

class DeleteGroupUseCase(private val groupRepositoryImpl: TeacherGroupRepositoryImpl) {
    operator fun invoke(groupIdentifier: String) {
        groupRepositoryImpl.deleteGroup(groupIdentifier)
    }
}