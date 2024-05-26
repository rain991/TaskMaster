package com.example.taskmaster.presentation.states.teacher

import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.presentation.UiText.UiText

data class TeacherAnswerScreenState (
    val currentStudentAnswer: StudentAnswer?,
    val fetchedStudentName : String?,
    val fetchedTaskName : String?,
    val teacherAnswer: String,
    val teacherGrade : String,
    val warningMessage : UiText?
)