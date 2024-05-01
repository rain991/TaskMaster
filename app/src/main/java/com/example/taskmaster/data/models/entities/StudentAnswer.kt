package com.example.taskmaster.data.models.entities

// ensure about max 5 unique files
data class StudentAnswer(
    val isAccepted: Boolean,
    val taskIdentifier: String,
    val studentUid: String,
    val answer: String?,
    val teacherComment: String? = null,
    val grade: Float,
    val fileUrls: List<String> // it is list of URL firebase storage reference, dont use this for local URI
) {
    constructor() : this(false, "", "", "", "", 0f, listOf<String>())
}