package com.example.taskmaster.presentation.states.teacher


import android.net.Uri
import java.util.Date

data class CreateTaskScreenState (
    val title : String,
    val description : String,
    val listOfGroupIdentifiers : List<String>,
    val selectedDate : Date,
    val attachedFiles : List<Uri>,
    val warningMessage : String? = null
)