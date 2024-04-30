package com.example.taskmaster.domain.useCases.student

import android.content.Context
import android.net.Uri
import com.example.taskmaster.data.implementations.core.student.tasks.StudentAnswerRepositoryImpl
import com.example.taskmaster.data.models.entities.StudentAnswer

class SubmitTaskUseCase(private val studentAnswerRepositoryImpl: StudentAnswerRepositoryImpl) {
    suspend operator fun invoke(studentAnswer: StudentAnswer, localUriFilesList: List<Uri>, context: Context) {
        studentAnswerRepositoryImpl.addAnswer(studentAnswer, localUriFilesList, context)
    }
}