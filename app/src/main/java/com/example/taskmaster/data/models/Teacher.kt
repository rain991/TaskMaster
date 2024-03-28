package com.example.taskmaster.data.models

data class Teacher(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val classes: List<Group>
)
