package com.example.taskmaster.data.models

data class Group(
    val id: Int,
    val name: String,
    val identifier: String,
    val teacher: Teacher,
    val students: List<Student>,
    val tasks: List<Task>
)
