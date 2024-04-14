package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.domain.useCases.teacher.group.DeletePersonFromGroupUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GroupDetailedScreenViewModel(
    private val auth: FirebaseAuth,
    private val deletePersonFromGroupUseCase: DeletePersonFromGroupUseCase,
) : ViewModel() {
    private val _currentDetailedGroup = MutableStateFlow<Group?>(null)
    val currentDetailedGroup = _currentDetailedGroup.asStateFlow()

    private val _searchedStudentsList = mutableStateListOf<Student>()
    val searchedStudentsList: List<Student> = _searchedStudentsList

    private val _listOfGroupStudents = mutableStateListOf<Student>()
    val listOfGroupStudents: List<Student> = _listOfGroupStudents

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _warningMessage = MutableStateFlow("")
    val warningMessage = _warningMessage.asStateFlow()

    private val currentFirebaseUser = auth.currentUser

    fun setCurrentDetailedGroup(group: Group?) {
        _currentDetailedGroup.value = group
    }

    suspend fun deleteStudentFromGroup(student: Student) {
        currentDetailedGroup.value?.let { deletePersonFromGroupUseCase(student, it.identifier) }
    }

    fun deleteStudentFromAddedList(student: Student) {
        _listOfGroupStudents.remove(student)
    }

    fun searchStudent(searchText: String) {
        setStudentsSearchedList(_listOfGroupStudents.filter {
            it.name.contains(searchText) || it.surname.contains(searchText) || it.email.contains(
                searchText
            )
        })
    }

    fun deleteWarningMessage() {
        _warningMessage.value = ""
    }

    fun setSearchText(value: String) {
        _searchText.value = value
        if (value.isEmpty()) {
            clearStudentsSearchedList()
        }
    }

    private fun setStudentsList(listOfGroupStudents: List<Student>) {
        _listOfGroupStudents.clear()
        _listOfGroupStudents.addAll(listOfGroupStudents)
    }

    private fun clearStudentsSearchedList() {
        _searchedStudentsList.clear()
    }

    private fun setWarningMessage(value: String) {
        _warningMessage.value = value
    }

    private fun setStudentsSearchedList(listOfSearchedStudents: List<Student>) {
        _searchedStudentsList.clear()
        _searchedStudentsList.addAll(listOfSearchedStudents)
    }
}