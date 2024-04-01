package com.example.taskmaster.data.models.entities

data class Group(
    val identifier: String,
    val name: String,
    val teacher: Teacher,
    val students: List<Student>,
    val tasks: List<Task>
)
