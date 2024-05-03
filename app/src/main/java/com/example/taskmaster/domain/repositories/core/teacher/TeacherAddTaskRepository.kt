package com.example.taskmaster.domain.repositories.core.teacher

import android.content.Context
import android.net.Uri
import com.example.taskmaster.data.models.entities.Task

interface TeacherAddTaskRepository {
    suspend fun addTask(task: Task, localUriFilesList: List<Uri>, context: Context) // addTask calls addFilesToStorage and saveDownloadUrlToTask
    suspend fun addFilesToStorage(uriList: List<Uri>, context: Context) : List<String> // adds files to storage and references to firestore
}