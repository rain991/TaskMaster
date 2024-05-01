package com.example.taskmaster.data.implementations.core.student.answers

import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.student.StudentRelatedAnswerListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class StudentsRelatedAnswerListRepositoryImpl(
    private val database: FirebaseFirestore,
    private val listenersManagerViewModel: ListenersManagerViewModel
) : StudentRelatedAnswerListRepository {
    override suspend fun getStudentRelatedAnswerList(studentUID: String) = callbackFlow {
        val answersCollection = database.collection("answers")
        val listener = answersCollection
            .whereEqualTo("studentUid", studentUID)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val answers = querySnapshot.documents.mapNotNull { document ->
                            document.toObject<StudentAnswer>()
                        }
                        trySend(answers).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }



}