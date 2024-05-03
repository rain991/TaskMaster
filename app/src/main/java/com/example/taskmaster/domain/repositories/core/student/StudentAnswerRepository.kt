package com.example.taskmaster.domain.repositories.core.student

import android.content.Context
import android.net.Uri
import com.example.taskmaster.data.models.entities.StudentAnswer

interface StudentAnswerRepository {
    suspend fun addAnswer(studentAnswer: StudentAnswer, localUriFilesList: List<Uri>, context: Context)
    suspend fun addFilesToStorage(uriList: List<Uri>, context: Context): List<String>
}