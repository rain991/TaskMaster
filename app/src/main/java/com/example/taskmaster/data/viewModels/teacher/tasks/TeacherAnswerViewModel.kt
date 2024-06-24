package com.example.taskmaster.data.viewModels.teacher.tasks

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taskmaster.R
import com.example.taskmaster.data.components.files.FileDownloader
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.implementations.core.teacher.answers.TeacherAnswerRepositoryImpl
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.presentation.UiText.UiText
import com.example.taskmaster.presentation.states.teacher.TeacherAnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TeacherAnswerViewModel(private val teacherAnswerRepositoryImpl: TeacherAnswerRepositoryImpl) : ViewModel() {
    private val _teacherAnswerScreenState = MutableStateFlow<TeacherAnswerScreenState>(
        TeacherAnswerScreenState(
            currentStudentAnswer = null,
            fetchedStudentName = null,
            fetchedTaskName = null,
            teacherAnswer = "",
            teacherGrade = "",
            warningMessage = null
        )
    )
    val teacherAnswerScreenState = _teacherAnswerScreenState.asStateFlow()

    suspend fun grade() {
        if (_teacherAnswerScreenState.value.currentStudentAnswer != null) {
            try {
                val teacherGradeValue = _teacherAnswerScreenState.value.teacherGrade.toFloat()
                if (teacherGradeValue != 0.0f) {
                    teacherAnswerRepositoryImpl.setGradeWithComment(
                        _teacherAnswerScreenState.value.currentStudentAnswer!!.studentUid,
                        taskIdentifier = _teacherAnswerScreenState.value.currentStudentAnswer!!.taskIdentifier,
                        grade = teacherGradeValue,
                        comment = _teacherAnswerScreenState.value.teacherAnswer
                    )
                } else {
                    setWarningMessage(UiText(R.string.teacher_answer_grade_should_not_be_0))
                }
            } catch (e: NumberFormatException) {
                Log.d(COMMON_DEBUG_TAG, "converting error : ${e.message}")
            }
        }
    }

    suspend fun sentBack() {
        if (_teacherAnswerScreenState.value.currentStudentAnswer != null) {
            if (_teacherAnswerScreenState.value.teacherAnswer.isNotEmpty()) {
                teacherAnswerRepositoryImpl.sendBackWithComment(
                    _teacherAnswerScreenState.value.currentStudentAnswer!!.studentUid,
                    taskIdentifier = _teacherAnswerScreenState.value.currentStudentAnswer!!.taskIdentifier,
                    comment = _teacherAnswerScreenState.value.teacherAnswer
                )
            } else {
                setWarningMessage(UiText(R.string.teacher_answer_comment_should_not_be_empty))
            }
        }
    }


    suspend fun downloadStudentFiles(context: Context) {
        val fileDownloader = FileDownloader(context)
        if (_teacherAnswerScreenState.value.currentStudentAnswer?.fileUrls?.isNotEmpty() == true) {
            _teacherAnswerScreenState.value.currentStudentAnswer?.fileUrls?.forEachIndexed { index, url ->
                val fileName = url.substringAfter("files%2F").substringBefore("?alt")
                if (fileName.length < 2) {
                    fileDownloader.downloadFile(url)
                } else {
                    fileDownloader.downloadFile(url)
                }
            }
        }
    }

    fun deleteWarningMessage() {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(warningMessage = null) }
    }

    fun setCurrentStudentAnswer(value: StudentAnswer?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(currentStudentAnswer = value) }
    }

    fun setTeacherAnswer(value: String) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherAnswer = value)
    }

    fun setTeacherGrade(value: String) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherGrade = value)
    }

    fun setFetchedStudentName(value: String?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(fetchedStudentName = value) }
    }

    private fun setWarningMessage(value: UiText?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(warningMessage = value) }
    }
}