package com.example.taskmaster.domain.useCases.student

import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupRepositoryImpl

class AddToGroupByIdentifierUseCase(private val studentGroupRepositoryImpl: StudentGroupRepositoryImpl) {
    suspend operator fun invoke(studentEmail: String, groupIdentifier: String) {
        studentGroupRepositoryImpl.addToGroupByIdentifier(studentEmail, groupIdentifier)
    }
}