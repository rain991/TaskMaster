package com.example.taskmaster.domain.repositories.core.student

import com.example.taskmaster.data.models.entities.StudentAnswer
import kotlinx.coroutines.flow.Flow

interface StudentRelatedAnswerListRepository {
    suspend fun getStudentRelatedAnswerList(studentUID : String) : Flow<List<StudentAnswer>>
}