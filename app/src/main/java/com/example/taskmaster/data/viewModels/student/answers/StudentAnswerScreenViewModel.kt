package com.example.taskmaster.data.viewModels.student.answers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.files.FileDownloader
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.implementations.core.student.tasks.StudentAnswerRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.presentation.states.student.StudentAnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentAnswerScreenViewModel(
    private val studentAnswerRepositoryImpl: StudentAnswerRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    context : Context
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

    private val fileDownloader = FileDownloader(context)

    suspend fun addAnswer() {

    }

    suspend fun fetchTeacherName() {
        if (_studentAnswerScreenState.value.currentTask != null) {
            setTeacherFetchedName(teacherTaskRepositoryImpl.getTeacherNameByUid(_studentAnswerScreenState.value.currentTask!!.teacher))
        }
    }

    fun downloadTaskFiles() {
        if (_studentAnswerScreenState.value.currentTask?.relatedFilesURL?.isNotEmpty() == true) {
            _studentAnswerScreenState.value.currentTask?.relatedFilesURL!!.forEachIndexed { index, url ->
                val fileName = url.substringAfter("files%2F").substringBefore("?alt")
                if (fileName.length < 2) {
                    fileDownloader.downloadFile(url)
                } else {
                    fileDownloader.downloadFile(url)
                }
            }
            Log.d(COMMON_DEBUG_TAG, "downloadTaskFiles: files are downloaded")
        }
    }

    fun unAttachStudentFiles() {
        if (_studentAnswerScreenState.value.studentFiles.isNotEmpty()) {
            _studentAnswerScreenState.value = studentAnswerScreenState.value.copy(studentFiles = emptyList())
        } else {
            setWarningMessage("You have not attached files")
        }
    }

    fun setCurrentAnswerTask(task: Task?) {
        _studentAnswerScreenState.update { studentAnswerScreenState.value.copy(currentTask = task) }
        Log.d(COMMON_DEBUG_TAG, "StudentTaskAnswerScreenComponent: current task : ${_studentAnswerScreenState.value.currentTask}")
    }

    fun setStudentAnswer(value: String) {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(studentAnswer = value)
    }

    fun setAnswerFiles(value: List<Uri>) {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(studentFiles = value)
    }

    fun deleteWarningMessage() {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(warningMessage = null)
    }

    fun setWarningMessage(value: String) {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(warningMessage = value)
    }
    private fun setTeacherFetchedName(value : String){
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(fetchedTeacherName = value)
    }
}