package com.example.taskmaster.domain.repositories.core.teacher

import android.content.Context
import android.net.Uri
import com.example.taskmaster.data.models.entities.Task

interface AddTaskRepository {
    suspend fun addTask(task: Task) // addTask calls addFilesToStorage and saveDownloadUrlToTask
    suspend fun addFilesToStorage(uriList: List<Uri>, context : Context) // adds files to storage and references to firestore
    suspend fun saveDownloadUrlToTask(downloadUrls : List<String>, documentUID : String)
}