package com.example.taskmaster.data.models.entities

data class Teacher(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val classes: List<Group>
)
