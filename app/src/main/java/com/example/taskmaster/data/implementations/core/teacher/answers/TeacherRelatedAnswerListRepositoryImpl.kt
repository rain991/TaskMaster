package com.example.taskmaster.data.implementations.core.teacher.answers

import android.util.Log
import com.example.taskmaster.data.constants.ANSWERS_COLLECTION
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.constants.GROUPS_COLLECTION
import com.example.taskmaster.data.constants.QUERY_DEBUG_TAG
import com.example.taskmaster.data.implementations.core.teacher.other.TeacherSearchRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.data.models.entities.StudentAnswer
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.domain.repositories.core.teacher.TeacherRelatedAnswerListRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TeacherRelatedAnswerListRepositoryImpl(
    private val database: FirebaseFirestore,
    private val teacherSearchRepositoryImpl: TeacherSearchRepositoryImpl,
    private val listenersManagerViewModel: ListenersManagerViewModel
) : TeacherRelatedAnswerListRepository {
    override suspend fun getTeacherRelatedAnswerList(teacherTaskIdentifiers : List<String>) = callbackFlow {
        if(teacherTaskIdentifiers.isEmpty()){
            trySend(emptyList<StudentAnswer>())
            close()
            return@callbackFlow
        }
        val answersCollection = database.collection(ANSWERS_COLLECTION)
        val listener = answersCollection
            .whereIn("taskIdentifier", teacherTaskIdentifiers)
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
                        Log.d(COMMON_DEBUG_TAG, "getTeacherRelatedAnswerList: answers size is ${answers.size}")
                        trySend(answers).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }

    override suspend fun getAllStudentsFromGroups(listOfRelatedGroupsIdentifiers: List<String>): List<Student> {
        val groupCollection = database.collection(GROUPS_COLLECTION)
        val emailsList = mutableListOf<String>()
        try {
            for (groupId in listOfRelatedGroupsIdentifiers) {
                val querySnapshot = groupCollection.whereEqualTo("identifier", groupId).get().await()
                for (document in querySnapshot.documents) {
                    try {
                        val group = document.toObject<Group>()
                        group?.students?.let { emailsList.addAll(it) }
                    } catch (e: Exception) {
                        Log.d(QUERY_DEBUG_TAG, "Error converting document to Group", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error fetching groups for teacher", e)
        }
        val studentsList = mutableListOf<Student>()
        emailsList.distinct()
        emailsList.forEach { email ->
            studentsList.addAll(teacherSearchRepositoryImpl.searchStudentsByEmail(email))
        }
        studentsList.distinct()
        return studentsList
    }

    override suspend fun getStudentsEmailsFromGroup(groupIdentifier: String): List<String> {
        val groupCollection = database.collection(GROUPS_COLLECTION)
        return try {
            val querySnapshot = groupCollection.whereEqualTo("identifier", groupIdentifier).get().await()
            querySnapshot.documents.flatMap { document ->
                try {
                    val group = document.toObject<Group>()
                    group?.students ?: emptyList()
                } catch (e: Exception) {
                    Log.d(QUERY_DEBUG_TAG, "Error converting document to Group", e)
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.d(QUERY_DEBUG_TAG, "Error fetching groups for teacher", e)
            emptyList()
        }
    }

    override suspend fun getStudentsEmailsFlowFromGroup(groupIdentifier: String): Flow<List<String>> = callbackFlow{
        val groupCollection = database.collection(GROUPS_COLLECTION)
        val listener = groupCollection.whereEqualTo("identifier", groupIdentifier)
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (querySnapshot != null) {
                    try {
                        val groups = querySnapshot.documents.mapNotNull { document ->
                            document.toObject<Group>()
                        }
                        val listOfStudentsEmails = groups.flatMap {  it.students }
                        trySend(listOfStudentsEmails).isSuccess
                    } catch (e: Exception) {
                        close(e)
                    }
                }
            }
        listenersManagerViewModel.addNewListener(listener)
        awaitClose { listener.remove() }
    }
}