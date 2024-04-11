package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.abstractionLayer.User
import com.example.taskmaster.data.models.entities.Group

interface SearchRepository {
    fun searchTeacherGroupByName(name : String, teacherUID : String) : List<Group>

    fun searchStudentsByEmail(email : String) : List<User>
}