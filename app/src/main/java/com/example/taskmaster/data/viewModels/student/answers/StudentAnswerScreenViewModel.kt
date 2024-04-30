package com.example.taskmaster.data.viewModels.student.answers

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.files.FileDownloader
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.domain.useCases.student.SubmitTaskUseCase
import com.example.taskmaster.presentation.states.student.StudentAnswerScreenState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StudentAnswerScreenViewModel(
    private val submitTaskUseCase: SubmitTaskUseCase,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    private val auth: FirebaseAuth,
    context: Context
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

    suspend fun addAnswer(localContext: Context) {
        if (auth.currentUser?.uid != null && _studentAnswerScreenState.value.currentTask?.identifier != null) {

            if (_studentAnswerScreenState.value.studentAnswer != "" || _studentAnswerScreenState.value.studentFiles.isNotEmpty()) {
                val studentAnswer = StudentAnswer(
                    isAccepted = false,
                    taskIdentifier = _studentAnswerScreenState.value.currentTask!!.identifier,
                    studentUid = auth.currentUser!!.uid,
                    answer = _studentAnswerScreenState.value.studentAnswer,
                    teacherComment = null,
                    fileUrls = listOf()
                )
                submitTaskUseCase(studentAnswer, _studentAnswerScreenState.value.studentFiles, localContext)
                resetScreenState()
            } else {
                Toast.makeText(localContext, "Answer should not be empty or contain at least 1 file", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(localContext, "Authentication error", Toast.LENGTH_SHORT).show()
        }
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

    private fun setTeacherFetchedName(value: String) {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(fetchedTeacherName = value)
    }

    private fun resetScreenState() {
        _studentAnswerScreenState.value = _studentAnswerScreenState.value.copy(
            studentAnswer = "",
            studentFiles = listOf()
        )
    }
}