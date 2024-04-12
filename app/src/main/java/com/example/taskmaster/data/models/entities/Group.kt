package com.example.taskmaster.data.models.entities

data class Group(
    val identifier: String,
    val name: String,
    val teacher: String,  // UID
    val students: List<String>, // students email
    val tasks: List<String> // tasks
)
