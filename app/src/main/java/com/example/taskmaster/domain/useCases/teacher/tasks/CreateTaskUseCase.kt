package com.example.taskmaster.domain.useCases.teacher.tasks

import android.content.Context
import android.net.Uri
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherAddTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Task

class CreateTaskUseCase(private val addTaskRepositoryImpl: TeacherAddTaskRepositoryImpl) {
    suspend operator fun invoke(task: Task, localUriFilesList: List<Uri>, context: Context) {
        addTaskRepositoryImpl.addTask(task, localUriFilesList, context)
    }
}