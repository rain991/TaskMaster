package com.example.taskmaster.presentation.states.teacher


import android.net.Uri
import java.util.Date

data class CreateTaskScreenState (
    val title : String,
    val description : String,
    val listOfGroupIdentifiers : List<String>,
    val selectedDate : Date? = null,
    val attachedFiles : List<Uri>,
    val datePickerState : Boolean = false,
    val timePickerState : Boolean = false,
    val groupPickerState : Boolean = false,
    val warningMessage : String? = null
)