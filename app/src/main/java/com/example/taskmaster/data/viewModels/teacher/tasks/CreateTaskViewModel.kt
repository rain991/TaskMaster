package com.example.taskmaster.data.viewModels.teacher.tasks

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.GroupsListRepositoryImpl
import com.example.taskmaster.presentation.states.teacher.CreateTaskScreenState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CreateTaskViewModel(private val groupsListRepositoryImpl: GroupsListRepositoryImpl, private val auth: FirebaseAuth) : ViewModel() {
    private val _createTaskScreenState = MutableStateFlow(
        CreateTaskScreenState(
            title = "",
            description = "",
            listOfGroupNames = listOf(),
            listOfSelectedGroupNames = listOf(),
            selectedDate = null,
            attachedFiles = listOf<Uri>(),
            isGroupSelectorShown = false
        )
    )
    val createTaskScreenState = _createTaskScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            setGroupIdentifiers(groupsListRepositoryImpl.getGroupsRelatedToTeacher(auth.currentUser!!.uid).map { it.name })
        }
    }

    fun setTitle(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(title = value)
    }

    fun setDescription(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(description = value)
    }

    fun setGroupIdentifiers(value: List<String>) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(listOfGroupNames = value)
    }

    fun setSelectedDate(value: Date) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(selectedDate = value)
    }

    fun setAttachedFiles(value: List<Uri>) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = value)
    }

    fun setDatePickerState(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(datePickerState = value)
    }

    fun setTimePickerState(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(timePickerState = value)
    }

    fun setIsGroupSelectorShown(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(isGroupSelectorShown = value)
    }

    fun setGroupAsSelected(groupName: String) {
        val listOfSelectedGroupNames = _createTaskScreenState.value.listOfSelectedGroupNames.toMutableList()
        if (!listOfSelectedGroupNames.contains(groupName)) {
            listOfSelectedGroupNames.add(groupName)
            _createTaskScreenState.value = createTaskScreenState.value.copy(listOfSelectedGroupNames = listOfSelectedGroupNames)
        }
    }

    fun setGroupAsUnselected(groupName: String) {
        val listOfSelectedNames = _createTaskScreenState.value.listOfSelectedGroupNames.toMutableList()
        if (listOfSelectedNames.contains(groupName)) {
            listOfSelectedNames.remove(groupName)
            _createTaskScreenState.value = createTaskScreenState.value.copy(listOfSelectedGroupNames = listOfSelectedNames)
        }
    }

    fun deleteTaskUri(value: Uri) {
        val attachedFiles = _createTaskScreenState.value.attachedFiles.toMutableList()
        attachedFiles.remove(value)
        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = attachedFiles)
    }
}