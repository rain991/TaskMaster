package com.example.taskmaster.data.viewModels.teacher.tasks

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.files.FileDownloader
import com.example.taskmaster.data.implementations.core.teacher.answers.TeacherAnswerRepositoryImpl
import com.example.taskmaster.data.models.entities.StudentAnswer
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
            teacherGrade = 0.0f,
            warningMessage = null
        )
    )
    val teacherAnswerScreenState = _teacherAnswerScreenState.asStateFlow()



    suspend fun grade() {
        if (_teacherAnswerScreenState.value.currentStudentAnswer != null) {
            if (_teacherAnswerScreenState.value.teacherGrade != 0.0f) {
                teacherAnswerRepositoryImpl.setGradeWithComment(
                    _teacherAnswerScreenState.value.currentStudentAnswer!!.studentUid,
                    taskIdentifier = _teacherAnswerScreenState.value.currentStudentAnswer!!.taskIdentifier,
                    grade = _teacherAnswerScreenState.value.teacherGrade,
                    comment = _teacherAnswerScreenState.value.teacherAnswer
                )
            } else {
                setWarningMessage("Grade should not be 0")
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
                setWarningMessage("Comment should not be empty")
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
 fun deleteWarningMessage(){
     _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(warningMessage = null) }
 }
    fun setCurrentStudentAnswer(value: StudentAnswer?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(currentStudentAnswer = value) }
    }

    fun setTeacherAnswer(value: String) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherAnswer = value)
    }

    fun setTeacherGrade(value: Float) {
        _teacherAnswerScreenState.value = teacherAnswerScreenState.value.copy(teacherGrade = value)
    }

    fun setFetchedStudentName(value: String?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(fetchedStudentName = value) }
    }

    private fun setWarningMessage(value: String?) {
        _teacherAnswerScreenState.update { teacherAnswerScreenState.value.copy(warningMessage = value) }
    }
}