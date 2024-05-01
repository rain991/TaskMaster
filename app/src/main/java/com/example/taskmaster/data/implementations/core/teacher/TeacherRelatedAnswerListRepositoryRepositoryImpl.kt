package com.example.taskmaster.data.implementations.core.teacher

import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.teacher.TeacherRelatedAnswerListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class TeacherRelatedAnswerListRepositoryRepositoryImpl(
    private val database: FirebaseFirestore,
    private val listenersManagerViewModel: ListenersManagerViewModel
) : TeacherRelatedAnswerListRepository {
    override suspend fun getTeacherRelatedAnswerList(teacherTaskIdentifiers : List<String>) = callbackFlow {
        val answersCollection = database.collection("answers")
        val listener = answersCollection
            .whereArrayContains("taskIdentifier", teacherTaskIdentifiers)
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