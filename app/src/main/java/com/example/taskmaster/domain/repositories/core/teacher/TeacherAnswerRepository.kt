package com.example.taskmaster.domain.repositories.core.teacher

interface TeacherAnswerRepository {
    suspend fun setGradeWithComment(studentUID: String, taskIdentifier : String, grade : Float, comment : String )
    suspend fun sendBackWithComment(studentUID: String, taskIdentifier : String, comment : String)
}