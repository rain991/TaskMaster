package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.other.PersonRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.GroupRepositoryImpl
import com.example.taskmaster.data.models.entities.Student

class DeletePersonFromGroupUseCase(private val groupRepositoryImpl: GroupRepositoryImpl, private val personRepositoryImpl: PersonRepositoryImpl) {
    suspend operator fun invoke(student : Student, groupIdentifier: String) {
        val currentPersonUID = personRepositoryImpl.findPersonUIDByEmail(student.email)
        groupRepositoryImpl.deleteStudentFromGroup(currentPersonUID, groupIdentifier)
    }
}