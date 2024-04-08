package com.example.taskmaster.data.viewModels.teacher

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.components.converters.convertLocalDateToDate
import com.example.taskmaster.presentation.states.teacher.CreateTaskScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

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








}