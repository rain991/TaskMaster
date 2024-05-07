package com.example.taskmaster.data.implementations.core.student.groups

import com.example.taskmaster.domain.repositories.core.student.StudentGroupRepository
import com.google.firebase.firestore.FirebaseFirestore

class StudentGroupRepositoryImpl(private val database: FirebaseFirestore) : StudentGroupRepository {
    override suspend fun addToGroupByIdentifier(studentEmail: String, groupIdentifier: String) {
        val groupsCollection = database.collection("groups")
        val groupQuery = groupsCollection.whereEqualTo("identifier", groupIdentifier).limit(1)
        groupQuery.get().addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                val groupDoc = documents.documents[0]
                val groupId = groupDoc.id
                val studentsList =
                    groupDoc.get("students") as MutableList<String> ?: mutableListOf()
                val isAppliable = groupDoc.get("appliable") as Boolean
                if (!studentsList.contains(studentEmail) && isAppliable) {
                    studentsList.add(studentEmail)
                    groupsCollection.document(groupId).update("students", studentsList)
                }
            }
        }
    }
}