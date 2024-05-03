package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.data.models.entities.Group

interface TeacherSearchRepository {
    fun searchTeacherGroupByName(name : String, teacherUID : String) : List<Group>

    suspend fun searchStudentsByEmail(email : String) : List<User>
}