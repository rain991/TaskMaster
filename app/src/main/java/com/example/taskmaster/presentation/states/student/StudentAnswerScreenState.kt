package com.example.taskmaster.presentation.states.student

import android.net.Uri
import com.example.taskmaster.data.models.entities.Task

data class StudentAnswerScreenState(
    val currentTask : Task?,
    val fetchedTeacherName : String?,
    val studentAnswer : String,
    val studentFiles : List<Uri>,
    val warningMessage : String?
)
