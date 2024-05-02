package com.example.taskmaster.data.viewModels.teacher.tasks

import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.presentation.states.teacher.TeacherAnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TeacherAnswerViewModel : ViewModel() {
    private val _teacherAnswerScreenState = MutableStateFlow<TeacherAnswerScreenState>(
        TeacherAnswerScreenState(
            currentStudentAnswer = null,
            fetchedStudentName = null,
            teacherAnswer = "",
            teacherGrade = 0.0f
        )
    )
    val teacherAnswerScreenState = _teacherAnswerScreenState.asStateFlow()

    fun setCurrentStudentAnswer(value: StudentAnswer?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(currentStudentAnswer = value) }
    }

    fun setTeacherAnswer(value: String) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherAnswer = value)
    }

    fun setTeacherGrade(value: Float) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherGrade = value)
    }

    private fun setFetchedStudentName(value: String?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(fetchedStudentName = value) }
    }
}