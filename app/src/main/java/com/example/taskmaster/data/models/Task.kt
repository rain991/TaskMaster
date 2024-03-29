package com.example.taskmaster.data.models

data class Task(
    val id: Int,
    val name: String,
    val description : String,
    val group: Group,
    val teacher: Teacher,
    val isActive: Boolean,
    val endDate: Long   //(in app java.util.date)
)
