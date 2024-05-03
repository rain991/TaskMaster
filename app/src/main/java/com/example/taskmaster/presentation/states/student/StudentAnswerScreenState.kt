package com.example.taskmaster.presentation.states.student

import android.net.Uri
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.presentation.UiText.UiText

data class StudentAnswerScreenState(
    val currentTask : Task?,
    val fetchedTeacherName : String?,
    val studentAnswer : String,
    val studentFiles : List<Uri>,
    val warningMessage : UiText?
)
