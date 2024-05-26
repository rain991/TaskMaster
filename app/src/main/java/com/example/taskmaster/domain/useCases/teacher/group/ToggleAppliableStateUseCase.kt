package com.example.taskmaster.domain.useCases.teacher.group

import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupRepositoryImpl

class ToggleAppliableStateUseCase(private val teacherGroupRepositoryImpl: TeacherGroupRepositoryImpl) {
    suspend operator fun invoke(state: Boolean, groupIdentifier: String) {
        teacherGroupRepositoryImpl.setAppliableGroupState(state, groupIdentifier)
    }
}