package com.example.taskmaster.presentation.states.teacher


import android.net.Uri
import com.example.taskmaster.presentation.UiText.UiText

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
    val warningMessage : UiText? = null
)