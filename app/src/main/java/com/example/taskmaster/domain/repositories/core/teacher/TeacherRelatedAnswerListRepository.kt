package com.example.taskmaster.domain.repositories.core.teacher

import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.StudentAnswer
import kotlinx.coroutines.flow.Flow

interface TeacherRelatedAnswerListRepository {
    suspend fun getTeacherRelatedAnswerList(teacherTaskIdentifiers : List<String>) : Flow<List<StudentAnswer>>
    suspend fun getAllStudentsFromGroups(listOfRelatedGroupsIdentifiers : List<String>): List<Student>
    suspend fun getStudentsEmailsFromGroup(groupIdentifier : String) : List<String>

}