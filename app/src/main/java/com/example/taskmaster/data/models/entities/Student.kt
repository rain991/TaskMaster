package com.example.taskmaster.data.models.entities

data class Student(
    val userType : String,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val groups: List<Group>,
    val tasks: List<Task>
)
