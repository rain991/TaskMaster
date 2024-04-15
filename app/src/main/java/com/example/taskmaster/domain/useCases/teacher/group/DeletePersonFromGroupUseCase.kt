package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.GroupRepositoryImpl

class DeletePersonFromGroupUseCase(private val groupRepositoryImpl: GroupRepositoryImpl) {
     operator fun invoke(studentEmail : String, groupIdentifier: String) {
        groupRepositoryImpl.deleteStudentFromGroup(studentEmail, groupIdentifier)
    }
}