package com.example.taskmaster.data.implementations.core.teacher.groups

import android.util.Log
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.teacher.TeacherGroupRepository
import com.google.firebase.firestore.FirebaseFirestore

class TeacherGroupRepositoryImpl(private val database: FirebaseFirestore) : TeacherGroupRepository {
    override fun createGroup(group: Group) {
        val groupCollection = database.collection("groups")
        val groupId = database.collection("groups").document().id
        val groupWithId = group.copy(identifier = groupId)
        groupCollection.add(groupWithId).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(COMMON_DEBUG_TAG, "createGroup: ${group.name} is successful")
            } else {
                Log.d(COMMON_DEBUG_TAG, "createGroup: ${group.name} error occurred")
            }
        }
    }

    override fun deleteGroup(groupIdentifier: String) {
        val groupCollection = database.collection("groups")
        val groupQuery = groupCollection.whereEqualTo("identifier", groupIdentifier)

        groupQuery.get().addOnSuccessListener { querySnapshot ->
            val groupDocument = querySnapshot.documents.firstOrNull()
            groupDocument?.let { document ->
                document.reference.delete().addOnSuccessListener {
                    Log.d(COMMON_DEBUG_TAG, "Group $groupIdentifier deleted successfully")
                }.addOnFailureListener { e ->
                    Log.d(COMMON_DEBUG_TAG, "Error deleting group $groupIdentifier: $e")
                }
            } ?: run {
                Log.d(COMMON_DEBUG_TAG, "Group $groupIdentifier not found")
            }
        }.addOnFailureListener { e ->
            Log.d(COMMON_DEBUG_TAG, "Error fetching group document: $e")
        }
    }

    override fun deleteStudentFromGroup(studentUID: String, groupIdentifier: String) {
        val groupCollection = database.collection("groups")
        val groupQuery = groupCollection.whereEqualTo("identifier", groupIdentifier)

        groupQuery.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents.firstOrNull()
                documentSnapshot?.let { document ->
                    val group = document.toObject(Group::class.java)
                    group?.let { groupData ->
                        val updatedStudents = groupData.students.filter { email -> email != studentUID }
                        val updatedGroup = groupData.copy(students = updatedStudents)
                        document.reference.set(updatedGroup)
                            .addOnSuccessListener {
                                Log.d(COMMON_DEBUG_TAG, "Student $studentUID removed from group $groupIdentifier successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.d(COMMON_DEBUG_TAG, "Error removing student $studentUID from group $groupIdentifier: $e")
                            }
                    }
                }
            } else {
                Log.d(COMMON_DEBUG_TAG, "Group $groupIdentifier does not exist.")
            }
        }.addOnFailureListener { e ->
            Log.d(COMMON_DEBUG_TAG, "Error getting group document: $e")
        }
    }
}