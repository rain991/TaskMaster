package com.example.taskmaster.data.viewModels.teacher.tasks

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.files.FileDownloader
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.presentation.states.teacher.TeacherAnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TeacherAnswerViewModel() : ViewModel() {
    private val _teacherAnswerScreenState = MutableStateFlow<TeacherAnswerScreenState>(
        TeacherAnswerScreenState(
            currentStudentAnswer = null,
            fetchedStudentName = null,
            fetchedTaskName = null,
            teacherAnswer = "",
            teacherGrade = 0.0f
        )
    )
    val teacherAnswerScreenState = _teacherAnswerScreenState.asStateFlow()


    init {

    }


    suspend fun grade() {

    }

    suspend fun sentBack(){

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
}