package com.example.taskmaster.data.implementations.core.student.tasks

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.taskmaster.R
import com.example.taskmaster.data.components.files.getFileName
import com.example.taskmaster.data.constants.ANSWERS_COLLECTION
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.domain.repositories.core.student.StudentAnswerRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class StudentAnswerRepositoryImpl(private val database: FirebaseFirestore, private val storageRef: FirebaseStorage) :
    StudentAnswerRepository {
    override suspend fun addAnswer(studentAnswer: StudentAnswer, localUriFilesList: List<Uri>, context: Context) {
        val answersReference = database.collection(ANSWERS_COLLECTION)
        val storageUrlList = addFilesToStorage(localUriFilesList, context)
        val studentAnswerWithActualUrlList = studentAnswer.copy(fileUrls = storageUrlList)
        answersReference.add(studentAnswerWithActualUrlList).addOnCompleteListener {
            Toast.makeText(context, context.getString(R.string.answer_added), Toast.LENGTH_LONG).show()
        }.addOnFailureListener { exception ->
            Toast.makeText(context, context.getString(R.string.error_adding_answer, exception.message), Toast.LENGTH_LONG).show()
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