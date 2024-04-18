package com.example.taskmaster.data.implementations.core.teacher

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.taskmaster.data.components.files.getFileName
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.domain.repositories.core.teacher.AddTaskRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class AddTaskRepositoryImpl(private val database: FirebaseFirestore, private val storageRef: FirebaseStorage) : AddTaskRepository {
    override suspend fun addTask(task: Task, localUriFilesList: List<Uri>, context: Context) {
        val tasksReference = database.collection("tasks")

        val storageUrlList = addFilesToStorage(localUriFilesList, context)
//        if (storageUrlList.isEmpty()) {
//            Log.d(COMMON_DEBUG_TAG, "addTask: Storage url list is empty after adding files")
//            return
//        } else {
            val taskWithActualURLList = task.copy(relatedFilesURL = storageUrlList)
            tasksReference.add(taskWithActualURLList).addOnCompleteListener { Log.d(COMMON_DEBUG_TAG, "Successful adding new task") }
                .addOnFailureListener { exception ->
                    Log.d(COMMON_DEBUG_TAG, "Error adding new task: ", exception)
                }

    }

    override suspend fun addFilesToStorage(uriList: List<Uri>, context: Context): List<String> {
        val urlList = mutableListOf<String>()
        for (uri in uriList) {
            withContext(Dispatchers.IO) {
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)

                    val fileName = uri.getFileName(context) ?: "file"

                    val fileRef = storageRef.getReference("files/$fileName")
                    val uploadTask = fileRef.putStream(inputStream!!)
                    uploadTask.await()

                    inputStream.close()

                    val downloadUrl = fileRef.downloadUrl.await()
                    urlList.add(downloadUrl.toString())
                } catch (e: IOException) {
                    Log.d(COMMON_DEBUG_TAG, "addFilesToStorage: error while adding task files to storage ", e)
                }
            }
        }
        return urlList
    }
}
