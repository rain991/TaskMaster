package com.example.taskmaster.data.viewModels.student.answers

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.implementations.core.student.tasks.StudentAnswerRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.presentation.states.student.StudentAnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentAnswerScreenViewModel(
    private val studentAnswerRepositoryImpl: StudentAnswerRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl
) : ViewModel() {
    private val _studentAnswerScreenState = MutableStateFlow(
        StudentAnswerScreenState(
            currentTask = null,
            fetchedTeacherName = null,
            studentAnswer = "",
            studentFiles = listOf(),
            warningMessage = null
        )
    )
    val studentAnswerScreenState = _studentAnswerScreenState.asStateFlow()

    suspend fun addAnswer() {

    }

    suspend fun fetchTeacherName() {
        if (_studentAnswerScreenState.value.currentTask != null) {
            teacherTaskRepositoryImpl.getTeacherNameByUid(_studentAnswerScreenState.value.currentTask!!.teacher)
        }
    }

    suspend fun downloadTaskFiles() {

    }

    fun attachFiles(){

    }
    fun unAttachStudentFiles() {

    }

    fun setCurrentAnswerTask(task: Task) {
        _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(currentTask = task)
    }

    fun setStudentAnswer(value: String) {
        _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(studentAnswer = value)
    }

    fun setAnswerFiles(value: List<Uri>) {
        _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(studentFiles = value)
    }

    fun deleteWarningMessage() {
        _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(warningMessage = null)
    }

    private fun setWarningMessage(value: String) {
        _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(warningMessage = value)
    }
}