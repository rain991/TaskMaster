package com.example.taskmaster.data.viewModels.teacher.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.tasks.TaskListRepositoryImpl
import com.example.taskmaster.data.models.entities.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskListRepositoryImpl: TaskListRepositoryImpl, private val auth : FirebaseAuth) : ViewModel() {
    private val _unfinishedTasksList = mutableStateListOf<Task>()
    val unfinishedTasksList: List<Task> = _unfinishedTasksList

    private val _finishedTasksList = mutableStateListOf<Task>()
    val finishedTasksList: List<Task> = _finishedTasksList

    private val currentTeacherUid = auth.currentUser?.uid
    init{
        viewModelScope.launch {
            taskListRepositoryImpl.getTeacherTasks(currentTeacherUid!!).collect { taskList ->
                val currentTimeMillis = System.currentTimeMillis()
                setFinishedTaskList(taskList.filter { it.endDate < currentTimeMillis })
                setUnfinishedTaskList(taskList.filter { it.endDate >= currentTimeMillis })
            }
        }
    }

    private fun setUnfinishedTaskList(list : List<Task>){
        _unfinishedTasksList.clear()
        _unfinishedTasksList.addAll(list)
    }
    private fun setFinishedTaskList(list : List<Task>){
        _finishedTasksList.clear()
        _finishedTasksList.addAll(list)
    }

}