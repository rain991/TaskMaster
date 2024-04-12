package com.example.taskmaster.data.implementations.core.teacher

import android.util.Log
import com.example.taskmaster.data.constants.SEARCH_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.Student
import com.example.taskmaster.domain.repositories.core.teacher.SearchRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SearchRepositoryImpl(private val database : FirebaseFirestore)  : SearchRepository{
    override fun searchTeacherGroupByName(name: String, teacherUID: String): List<Group> {
        val groupsCollection = database.collection("groups")
        val resultGroups = mutableListOf<Group>()

        groupsCollection
            .whereEqualTo("name", name)
            .whereEqualTo("teacherUID", teacherUID)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val group = document.toObject(Group::class.java)
                    resultGroups.add(group)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(SEARCH_DEBUG_TAG, "Error getting documents: ", exception)
            }
        return resultGroups
    }


    override suspend fun searchStudentsByEmail(email: String): List<Student> {
        val usersCollection = database.collection("users")
        val resultUsers = mutableListOf<Student>()

        usersCollection
            .whereGreaterThanOrEqualTo("email", email)
            .whereLessThan("email", email + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val user = document.toObject(Student::class.java)
                    resultUsers.add(user)
                }
                Log.d(SEARCH_DEBUG_TAG, "Successful search of users")
            }
            .addOnFailureListener { exception ->
                Log.d(SEARCH_DEBUG_TAG, "Error getting documents: ", exception)
            }
            .await()
        return resultUsers
    }
}