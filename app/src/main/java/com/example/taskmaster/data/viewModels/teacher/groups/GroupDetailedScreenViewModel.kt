package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.implementations.core.other.PersonRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.teacher.group.DeletePersonFromGroupUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GroupDetailedScreenViewModel(
    private val auth: FirebaseAuth,
    private val deletePersonFromGroupUseCase: DeletePersonFromGroupUseCase,
    private val personRepositoryImpl: PersonRepositoryImpl
) : ViewModel() {
    private val _currentDetailedGroup = MutableStateFlow<Group?>(null)
    val currentDetailedGroup = _currentDetailedGroup.asStateFlow()

    private val _searchedStudentsList = mutableStateListOf<String>()
    val searchedStudentsList: List<String> = _searchedStudentsList

    private val _listOfGroupStudents = mutableStateListOf<String>()
    val listOfGroupStudents: List<String> = _listOfGroupStudents

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _warningMessage = MutableStateFlow("")
    val warningMessage = _warningMessage.asStateFlow()

    private val currentFirebaseUser = auth.currentUser

    init {
        _currentDetailedGroup.value?.let { setStudentsList(it.students) }
    }

    fun setCurrentDetailedGroup(group: Group?) {
        _currentDetailedGroup.value = group
    }

    suspend fun deleteStudentFromGroup(studentEmail: String) {
       val deleteStudentUID=  personRepositoryImpl.findPersonUIDByEmail(studentEmail)
        currentDetailedGroup.value?.let { deletePersonFromGroupUseCase(deleteStudentUID, it.identifier) }
    }

    fun deleteStudentFromAddedList(studentEmail: String) {
        _listOfGroupStudents.remove(studentEmail)
    }



    fun deleteWarningMessage() {
        _warningMessage.value = ""
    }

    fun setSearchText(value: String) {
        _searchText.update{value}
    }
    fun searchStudent() {
        if (_searchText.value.isNotEmpty()) {
            setStudentsSearchedList(currentDetailedGroup.value!!.students.filter {
                it.contains(_searchText.value)
            })
        } else {
            clearStudentsSearchedList()
        }
    }
    private fun setStudentsSearchedList(listOfSearchedStudents: List<String>) {
        _searchedStudentsList.clear()
        _searchedStudentsList.addAll(listOfSearchedStudents)
    }
    private fun clearStudentsSearchedList() {
        _searchedStudentsList.clear()
    }
    private fun setStudentsList(listOfGroupStudents: List<String>) {
        _listOfGroupStudents.clear()
        _listOfGroupStudents.addAll(listOfGroupStudents)
    }



    private fun setWarningMessage(value: String) {
        _warningMessage.value = value
    }


}