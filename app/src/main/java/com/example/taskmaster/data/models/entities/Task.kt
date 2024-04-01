package com.example.taskmaster.data.models.entities

data class Task(
    val name: String,
    val description : String,
    val group: Group,
    val teacher: Teacher,
    val isActive: Boolean,
    val endDate: Long   //(in app java.util.date)
)
