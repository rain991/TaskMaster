package com.example.taskmaster.data.viewModels.teacher

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.converters.convertLocalDateToDate
import com.example.taskmaster.presentation.states.teacher.CreateTaskScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Date

class CreateTaskViewModel : ViewModel() {
    private val _createTaskScreenState = MutableStateFlow(
        CreateTaskScreenState(
            title = "",
            description = "",
            listOfGroupIdentifiers = listOf<String>(),
            selectedDate = convertLocalDateToDate(LocalDate.now()),
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
}