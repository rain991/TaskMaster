package com.example.taskmaster.data.models

data class Student(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val groups: List<Group>,
    val tasks: List<Task>
)
