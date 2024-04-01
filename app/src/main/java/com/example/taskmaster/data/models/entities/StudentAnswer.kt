package com.example.taskmaster.data.models.entities
// ensure about max 5 unique files
data class StudentAnswer (
    val task : Task,
    val student : Student,
    val answer : String,
    val fileUrls: List<String> = listOf()
)