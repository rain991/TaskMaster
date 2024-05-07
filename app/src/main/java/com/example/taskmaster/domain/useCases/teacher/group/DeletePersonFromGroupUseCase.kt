package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupRepositoryImpl

class DeletePersonFromGroupUseCase(private val groupRepositoryImpl: TeacherGroupRepositoryImpl) {
     suspend operator fun invoke(studentEmail : String, groupIdentifier: String) {
        groupRepositoryImpl.deleteStudentFromGroup(studentEmail, groupIdentifier)
    }
}