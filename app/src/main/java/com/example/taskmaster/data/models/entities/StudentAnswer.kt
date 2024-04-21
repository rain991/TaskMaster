package com.example.taskmaster.data.models.entities
// ensure about max 5 unique files
data class StudentAnswer (
    val isAccepted : Boolean,
    val taskIdentifier : String,
    val studentUid : String,
    val teacherComment : String?,
    val answer : String?,
    val fileUrls: List<String> = listOf()
)