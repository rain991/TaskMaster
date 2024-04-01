package com.example.taskmaster.data.models.entities
// ensure about max 5 unique files
data class StudentAnswer (
    val isAccepted : Boolean = false,
    val task : Task,
    val student : Student,
    val teacherComment : String?,
    val answer : String?,
    val fileUrls: List<String> = listOf()
)