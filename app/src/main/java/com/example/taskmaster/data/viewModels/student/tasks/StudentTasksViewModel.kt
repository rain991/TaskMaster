package com.example.taskmaster.data.viewModels.student.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.R
import com.example.taskmaster.data.constants.FINISHED_TASKS_DATA_REQUEST_TIME
import com.example.taskmaster.data.implementations.core.student.answers.StudentsRelatedAnswerListRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupListRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.tasks.StudentTaskListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.presentation.UiText.UiText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StudentTasksViewModel(
    private val studentTaskListRepositoryImpl: StudentTaskListRepositoryImpl,
    private val studentGroupListRepositoryImpl: StudentGroupListRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    private val studentsRelatedAnswerListRepositoryImpl: StudentsRelatedAnswerListRepositoryImpl,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _unfinishedTasksList = mutableStateListOf<Task>()
    val unfinishedTasksList: List<Task> = _unfinishedTasksList

    private val _finishedTasksList = mutableStateListOf<Task>()
    val finishedTasksList: List<Task> = _finishedTasksList

    private val _studentAnswersList = mutableStateListOf<StudentAnswer>()
    val studentAnswersList: List<StudentAnswer> = _studentAnswersList

    private val _studentGroups = mutableStateListOf<Group>()
    val studentGroups: List<Group> = _studentGroups

    private val _teacherUidToNameMap = mutableStateMapOf<String, String>() // uid to name + surname
    val teacherUidToNameMap: Map<String, String> = _teacherUidToNameMap

    private val _warningMessage = MutableStateFlow<UiText?>(null)
    val warningMessage = _warningMessage.asStateFlow()

    private val currentUser = auth.currentUser
    private val allStudentTasks = mutableStateListOf<Task>()

    init {
        viewModelScope.launch {
            if (currentUser?.email != null) {
                val currentTimeMillis = System.currentTimeMillis()
                studentGroupListRepositoryImpl.getStudentGroups(currentUser.email!!).collect { groups ->

                    setStudentGroupList(groups)
                    setAllStudentTasksList(
                        studentTaskListRepositoryImpl.getStudentTasks(_studentGroups.toList().map { it.identifier }).first()
                    )
                    setStudentsAnswerList(
                        studentsRelatedAnswerListRepositoryImpl.getStudentRelatedAnswerList(auth.currentUser!!.uid).first()
                    )
                    setFinishedTaskList(
                        allStudentTasks.toList().filter { task ->
                            task.endDate < currentTimeMillis || studentAnswersList.map { answer -> answer.taskIdentifier }
                                .contains(task.identifier)
                        })
                    setUnfinishedTaskList(allStudentTasks.toList().filter { it.endDate >= currentTimeMillis }
                        .filter { it -> !studentAnswersList.map { it.taskIdentifier }.contains(it.identifier) })
                }
            } else {
                setWarningMessage(UiText(R.string.student_tasks_invalid_current_user_error))
            }
        }
    }

    fun deleteWarningMessage() {
        _warningMessage.value = null
    }

    suspend fun initializeTeacherUidToNameMapForUnfinishedTasks() {
        if (unfinishedTasksList.isEmpty()) {
            delay(FINISHED_TASKS_DATA_REQUEST_TIME)
            initializeTeacherUidToNameMapForUnfinishedTasks()
            return
        }
        val teacherUidList = _unfinishedTasksList.map { it.teacher }
        viewModelScope.launch {
            teacherUidList.forEach {
                addTeacherUidToName(it, getTeacherNameByUid(it))
            }
        }
    }

    suspend fun initializeTeacherUidToNameMapForFinishedTasks() {
        if (finishedTasksList.isEmpty()) {
            delay(FINISHED_TASKS_DATA_REQUEST_TIME)
            initializeTeacherUidToNameMapForFinishedTasks()
            return
        }
        val teacherUidList = _finishedTasksList.map { it.teacher }
        viewModelScope.launch {
            teacherUidList.forEach {
                addTeacherUidToName(it, getTeacherNameByUid(it))
            }
        }
    }

    private suspend fun getTeacherNameByUid(uid: String): String {
        return teacherTaskRepositoryImpl.getTeacherNameByUid(uid)
    }

    private fun setUnfinishedTaskList(list: List<Task>) {
        _unfinishedTasksList.clear()
        _unfinishedTasksList.addAll(list)
    }

    private fun setFinishedTaskList(list: List<Task>) {
        _finishedTasksList.clear()
        _finishedTasksList.addAll(list)
    }

    private fun addTeacherUidToName(uid: String, name: String) {
        if (!_teacherUidToNameMap.containsKey(uid)) {
            _teacherUidToNameMap[uid] = name
        }
    }

    private fun setWarningMessage(value: UiText) {
        _warningMessage.value = value
    }

    private fun setStudentGroupList(value: List<Group>) {
        _studentGroups.clear()
        _studentGroups.addAll(value)
    }

    private fun setAllStudentTasksList(value: List<Task>) {
        allStudentTasks.clear()
        allStudentTasks.addAll(value)
    }

    private fun setStudentsAnswerList(value: List<StudentAnswer>) {
        _studentAnswersList.clear()
        _studentAnswersList.addAll(value)
    }
}