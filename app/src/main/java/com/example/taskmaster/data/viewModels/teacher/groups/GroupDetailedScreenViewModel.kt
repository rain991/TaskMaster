package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.answers.TeacherRelatedAnswerListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupsListRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.teacher.group.DeletePersonFromGroupUseCase
import com.example.taskmaster.presentation.UiText.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GroupDetailedScreenViewModel(
    private val deletePersonFromGroupUseCase: DeletePersonFromGroupUseCase,
    private val groupsListRepositoryImpl: TeacherGroupsListRepositoryImpl,
    private val teacherRelatedAnswerListRepositoryImpl: TeacherRelatedAnswerListRepositoryImpl
) : ViewModel() {
    private val _currentDetailedGroup = MutableStateFlow<Group?>(null)
    val currentDetailedGroup = _currentDetailedGroup.asStateFlow()

    private val _searchedStudentsList = mutableStateListOf<String>()
    val searchedStudentsList: List<String> = _searchedStudentsList

    private val _listOfGroupStudents = mutableStateListOf<String>()
    val listOfGroupStudents: List<String> = _listOfGroupStudents

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _warningMessage = MutableStateFlow<UiText?>(null)
    val warningMessage = _warningMessage.asStateFlow()

    init {
        viewModelScope.launch {
            _currentDetailedGroup.collect { currentGroup ->
                if (_currentDetailedGroup.value != null) {
                    teacherRelatedAnswerListRepositoryImpl.getStudentsEmailsFlowFromGroup(
                        currentGroup?.identifier ?: ""
                    ).collect {
                        setStudentsList(it)
                    }
                }
            }
        }
    }

    fun setCurrentDetailedGroup(group: Group?) {
        _currentDetailedGroup.value = group
    }

    suspend fun fetchStudentsList(groupIdentifier: String) {
        if (groupIdentifier.isNotEmpty()) {
            teacherRelatedAnswerListRepositoryImpl.getStudentsEmailsFlowFromGroup(groupIdentifier).collect {
                setStudentsList(it)
            }
        }
    }

    fun deleteStudentFromGroup(studentEmail: String) {
        currentDetailedGroup.value?.let { deletePersonFromGroupUseCase(studentEmail, it.identifier) }
    }

    fun deleteWarningMessage() {
        _warningMessage.value = null
    }

    fun setSearchText(value: String) {
        _searchText.update { value }
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

    fun clearStudentsList() {
        _listOfGroupStudents.clear()
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

    private fun setWarningMessage(value: UiText?) {
        _warningMessage.value = value
    }
}