package com.example.taskmaster.presentation.states.teacher

import com.example.taskmaster.data.models.entities.StudentAnswer

data class TeacherAnswerScreenState (
    val currentStudentAnswer: StudentAnswer?,
    val fetchedStudentName : String?,
    val teacherAnswer: String,
    val teacherGrade : Float
)