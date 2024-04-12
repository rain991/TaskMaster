package com.example.taskmaster.data.viewModels.teacher.tasks

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.taskmaster.presentation.states.teacher.CreateTaskScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class CreateTaskViewModel : ViewModel() {
    private val _createTaskScreenState = MutableStateFlow(
        CreateTaskScreenState(
            title = "",
            description = "",
            listOfGroupIdentifiers = listOf<String>(),
            selectedDate = null,
            attachedFiles = listOf<Uri>()
        )
    )
    val createTaskScreenState = _createTaskScreenState.asStateFlow()


    fun setTitle(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(title = value)
    }

    fun setDescription(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(description = value)
    }

    fun setGroupIdentifiers(value: List<String>) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(listOfGroupIdentifiers = value)
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

    fun setGroupPickerState(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(groupPickerState = value)
    }

    fun deleteTaskUri(value: Uri) {
        val attachedFiles = _createTaskScreenState.value.attachedFiles.toMutableList()
        attachedFiles.remove(value)
        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = attachedFiles)
    }
}