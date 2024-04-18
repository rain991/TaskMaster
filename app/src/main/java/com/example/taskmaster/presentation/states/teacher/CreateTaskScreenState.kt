package com.example.taskmaster.presentation.states.teacher


import android.net.Uri

data class CreateTaskScreenState (
    val title : String,
    val description : String,
    val isGroupSelectorShown : Boolean,
    val listOfGroupNames : List<String>,
    val listOfSelectedGroupNames : List<String>,
    val selectedDate : Long? = null,
    val attachedFiles : List<Uri>,
    val datePickerState : Boolean = false,
    val timePickerState : Boolean = false,
    val warningMessage : String? = null
)