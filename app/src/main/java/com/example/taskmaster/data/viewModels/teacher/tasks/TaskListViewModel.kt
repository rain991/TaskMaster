package com.example.taskmaster.data.viewModels.teacher.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.tasks.TaskListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskListRepositoryImpl: TaskListRepositoryImpl,
    private val taskRepositoryImpl: TaskRepositoryImpl,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _unfinishedTasksList = mutableStateListOf<Task>()
    val unfinishedTasksList: List<Task> = _unfinishedTasksList

    private val _finishedTasksList = mutableStateListOf<Task>()
    val finishedTasksList: List<Task> = _finishedTasksList

    private val _teacherUidToNameMap = mutableStateMapOf<String, String>() // uid to name + surname
    val teacherUidToNameMap: Map<String, String> = _teacherUidToNameMap

    private val _groupIdToNameMap = mutableStateMapOf<String, String>() // id to name
    val groupIdToNameMap: Map<String, String> = _groupIdToNameMap

    private val currentTeacherUid = auth.currentUser?.uid
    init {
        viewModelScope.launch {
            if (currentTeacherUid != null) {
                taskListRepositoryImpl.getTeacherTasks(currentTeacherUid!!).collect { taskList ->
                    val currentTimeMillis = System.currentTimeMillis()
                    setFinishedTaskList(taskList.filter { it.endDate < currentTimeMillis })
                    setUnfinishedTaskList(taskList.filter { it.endDate >= currentTimeMillis })
                }
            }
        }
    }



    suspend fun initializeTeacherUidToNameMapForUnfinishedTasks() {
        val teacherUidList = _unfinishedTasksList.toList().map { it.teacher }
        viewModelScope.launch {
            teacherUidList.forEach{
                addTeacherUidToName(it, getTeacherNameByUid(it))
            }
        }
    }

    suspend fun initializeGroupIdToNameMapForUnfinishedTasks() {
        val groupList = _unfinishedTasksList.toList().flatMap { it.groups }
        viewModelScope.launch {
            groupList.forEach{
                addGroupIdToName(it, getTeacherNameByUid(it))
            }
        }
    }

    suspend fun initializeTeacherUidToNameMapForFinishedTasks() {
        val teacherUidList = _finishedTasksList.toList().map { it.teacher }
        viewModelScope.launch {
            teacherUidList.forEach{
                addTeacherUidToName(it, getTeacherNameByUid(it))
            }
        }
    }

    suspend fun initializeGroupIdToNameMapForFinishedTasks() {
        val groupList = _finishedTasksList.toList().flatMap { it.groups }
        viewModelScope.launch {
            groupList.forEach{
                addGroupIdToName(it, getTeacherNameByUid(it))
            }
        }
    }

    private suspend fun getTeacherNameByUid(uid: String): String {
        return taskRepositoryImpl.getTeacherNameByUid(uid)
    }

    private suspend fun getGroupNameByIdentifier(identifier: String): String {
        return taskRepositoryImpl.getGroupNameByIdentifier(identifier)
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

    private fun addGroupIdToName(id: String, name: String) {
        if (!_teacherUidToNameMap.containsKey(id)) {
            _teacherUidToNameMap[id] = name
        }
    }

}