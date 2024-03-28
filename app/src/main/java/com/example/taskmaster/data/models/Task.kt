package com.example.taskmaster.data.models

data class Task(
    val id: Int,
    val name: String,
    val group: Group,
    val isActive: Boolean,
    val endDate: Long   //(in app java.util.date)
)
