package com.example.taskmaster.data.implementations.core.teacher.answers

import android.util.Log
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.domain.repositories.core.teacher.TeacherAnswerRepository
import com.google.firebase.firestore.FirebaseFirestore

class TeacherAnswerRepositoryImpl(private val database: FirebaseFirestore) : TeacherAnswerRepository {
    override suspend fun setGradeWithComment(studentUID: String, taskIdentifier: String, grade: Float, comment: String) {
        val answersCollection = database.collection("answers")
        val answerQuery = answersCollection.whereEqualTo("studentUid", studentUID).whereEqualTo("taskIdentifier", taskIdentifier).limit(1)
        answerQuery.get().addOnSuccessListener{
                documents ->
            if (!documents.isEmpty) {
                val answerDocument = documents.documents[0]
                val documentId = answerDocument.id
                answersCollection.document(documentId).update("teacherComment", comment)
                answersCollection.document(documentId).update("grade", grade)
                answersCollection.document(documentId).update("accepted", true)
            }
        }
    }

    override suspend fun sendBackWithComment(studentUID: String, taskIdentifier: String, comment: String) {
        Log.d(COMMON_DEBUG_TAG, "sendBackWithComment: studentUId: ${studentUID}")
        Log.d(COMMON_DEBUG_TAG, "sendBackWithComment: taskIdentifier: ${taskIdentifier}")
        val answersCollection = database.collection("answers")
        val answerQuery = answersCollection.whereEqualTo("studentUid", studentUID).whereEqualTo("taskIdentifier", taskIdentifier).limit(1)
        answerQuery.get().addOnSuccessListener{
                documents ->
            if (!documents.isEmpty) {
                val answerDocument = documents.documents[0]
                val documentId = answerDocument.id
                answersCollection.document(documentId).update("teacherComment", comment)
                answersCollection.document(documentId).update("accepted", false)
            }
        }
    }
}