package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.implementations.core.teacher.SearchRepositoryImpl
import com.example.taskmaster.data.models.entities.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateGroupViewModel(private val searchRepositoryImpl: SearchRepositoryImpl) : ViewModel() {
    private val _searchedStudentsList = mutableStateListOf<Student>()
    val searchedStudentsList: List<Student> = _searchedStudentsList

    private val _addedStudentsList = mutableStateListOf<Student>()
    val addedStudentsList: List<Student> = _addedStudentsList

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _warningMessage = MutableStateFlow("")
    val warningMessage = _warningMessage.asStateFlow()

    private val _groupNameText = MutableStateFlow("")
    val groupNameText = _groupNameText.asStateFlow()
    fun createGroup() {

    }

    fun addStudentFromSearchList(student: Student) {
        if(_addedStudentsList.contains(student)){
            setWarningMessage("This student is already added")
        }else{
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
        _warningMessage.value = ""
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

    private fun setWarningMessage(value: String) {
        _warningMessage.value = value
    }

    private fun setStudentsSearchedList(listOfSearchedStudents: List<Student>) {
        _searchedStudentsList.clear()
        _searchedStudentsList.addAll(listOfSearchedStudents)
    }

}