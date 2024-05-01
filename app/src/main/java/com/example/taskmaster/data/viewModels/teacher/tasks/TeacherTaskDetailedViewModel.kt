package com.example.taskmaster.data.viewModels.teacher.tasks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.answers.TeacherRelatedAnswerListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskListRepositoryImpl
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.models.entities.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TeacherTaskDetailedViewModel(
    private val teacherRelatedAnswerListRepositoryImpl: TeacherRelatedAnswerListRepositoryImpl,
    private val teacherTaskListRepositoryImpl: TeacherTaskListRepositoryImpl,
    private val auth: FirebaseAuth
) :
    ViewModel() {
    private val _currentTask = MutableStateFlow<Task?>(null)
    val currentTask = _currentTask.asSharedFlow()

    private val _taskRelatedAnswers = mutableStateListOf<StudentAnswer>()
    val taskRelatedAnswers: List<StudentAnswer> = _taskRelatedAnswers

    private val _studentsList = mutableStateListOf<Student>()
    val studentsList: List<Student> = _studentsList

    private val allTeacherRelatedAnswers = mutableListOf<StudentAnswer>()

    init {
        viewModelScope.launch {
            if (_currentTask.value != null && _currentTask.value?.groups?.isNotEmpty() == true && auth.currentUser?.uid != null) {
                teacherTaskListRepositoryImpl.getTeacherTasks(auth.currentUser?.uid!!).collect { listOfTeacherTasks ->
                    allTeacherRelatedAnswers.clear()
                    allTeacherRelatedAnswers.addAll(
                        teacherRelatedAnswerListRepositoryImpl.getTeacherRelatedAnswerList(
                            listOfTeacherTasks.map { it.identifier }).first()
                    )
                    setStudentsList(teacherRelatedAnswerListRepositoryImpl.getAllStudentsFromGroups(listOfRelatedGroupsIdentifiers = _currentTask.value!!.groups))
                    setTaskRelatedAnswers(allTeacherRelatedAnswers.filter { studentAnswer ->
                        studentAnswer.taskIdentifier == _currentTask.value!!.identifier
                    })
                }
            }
        }

    }

    private fun setCurrentTask(value: Task?) {
        _currentTask.value = value
    }

    private fun setTaskRelatedAnswers(value: List<StudentAnswer>) {
        _taskRelatedAnswers.clear()
        _taskRelatedAnswers.addAll(value)
    }

    private fun setStudentsList(value: List<Student>) {
        _studentsList.clear()
        _studentsList.addAll(value)
    }

}