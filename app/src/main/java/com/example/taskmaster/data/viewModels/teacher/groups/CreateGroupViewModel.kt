package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.R
import com.example.taskmaster.data.implementations.core.teacher.other.TeacherSearchRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.domain.useCases.teacher.group.CreateGroupUseCase
import com.example.taskmaster.presentation.UiText.UiText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateGroupViewModel(
    private val searchRepositoryImpl: TeacherSearchRepositoryImpl,
    private val createGroupUseCase: CreateGroupUseCase,
    private val auth: FirebaseAuth
) :
    ViewModel() {
    private val _searchedStudentsList = mutableStateListOf<Student>()
    val searchedStudentsList: List<Student> = _searchedStudentsList

    private val _addedStudentsList = mutableStateListOf<Student>()
    val addedStudentsList: List<Student> = _addedStudentsList

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _warningMessage = MutableStateFlow<UiText?>(null)
    val warningMessage = _warningMessage.asStateFlow()

    private val _groupNameText = MutableStateFlow("")
    val groupNameText = _groupNameText.asStateFlow()
    fun createGroup() {
        if(_addedStudentsList.size>0 && _groupNameText.value.length > 2){
            createGroupUseCase(
                Group(
                    identifier = "",
                    name = _groupNameText.value,
                    teacher = auth.currentUser!!.uid,
                    students = _addedStudentsList.map { it.email },
                    tasks = listOf()
                )
            )
            setWarningMessage(UiText(R.string.create_group_group_created_message, _groupNameText.value))  //"Group ${_groupNameText.value} created"

        }else{
            setWarningMessage(UiText(R.string.create_group_error_1))
        }
    }

    fun addStudentFromSearchList(student: Student) {
        if (_addedStudentsList.contains(student)) {
            setWarningMessage(UiText(R.string.create_group_student_already_added_error))
        } else {
            _addedStudentsList.add(student)
        }
    }

    fun deleteStudentFromAddedList(student: Student) {
        _addedStudentsList.remove(student)
    }

    suspend fun searchStudent(studentEmail: String) {
        setStudentsSearchedList(searchRepositoryImpl.searchStudentsByEmail(studentEmail))
    }

    fun deleteWarningMessage() {
        _warningMessage.value = null
    }

    fun clearStudentsSearchedList() {
        _searchedStudentsList.clear()
    }

    fun setSearchText(value: String) {
        _searchText.value = value
    }

    fun setGroupName(value: String) {
        _groupNameText.value = value
    }

    private fun setWarningMessage(value: UiText?) {
        _warningMessage.value = value
    }

    private fun setStudentsSearchedList(listOfSearchedStudents: List<Student>) {
        _searchedStudentsList.clear()
        _searchedStudentsList.addAll(listOfSearchedStudents)
    }

}