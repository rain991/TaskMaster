package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.GroupRepositoryImpl

class DeletePersonFromGroupUseCase(private val groupRepositoryImpl: GroupRepositoryImpl) {
    suspend operator fun invoke(studentUID : String, groupIdentifier: String) {
        groupRepositoryImpl.deleteStudentFromGroup(studentUID, groupIdentifier)
    }
}