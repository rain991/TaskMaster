package com.example.taskmaster.data.implementations.core.teacher

import android.util.Log
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.repositories.core.teacher.GroupRepository
import com.google.firebase.firestore.FirebaseFirestore

class GroupRepositoryImpl(private val database : FirebaseFirestore) : GroupRepository {
    override fun createGroup(group: Group) {
        val groupCollection = database.collection("groups")
        val groupId = database.collection("groups").document().id
        val groupWithId = group.copy(identifier = groupId)
        groupCollection.add(groupWithId).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d(COMMON_DEBUG_TAG, "createGroup: ${group.name} is successful")
            }else{
                Log.d(COMMON_DEBUG_TAG, "createGroup: ${group.name} error occurred")
            }
        }
    }

    override fun deleteGroup(groupIdentifier : String) {
        val groupCollection = database.collection("groups")
        val groupDocument = groupCollection.document(groupIdentifier) // Assuming group.id is the document ID
        groupDocument.delete().addOnCompleteListener {
            if(it.isSuccessful){
                Log.d(COMMON_DEBUG_TAG, "deleteGroup: $groupIdentifier is successful")
            } else {
                Log.d(COMMON_DEBUG_TAG, "deleteGroup: $groupIdentifier error occurred")
            }
        }
    }
override fun deleteStudentFromGroup(studentUID: String, groupIdentifier : String) {
    val groupCollection = database.collection("groups")
    val groupDocument = groupCollection.document(groupIdentifier)
    groupDocument.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val group = documentSnapshot.toObject(Group::class.java)
            group?.let {
                val updatedStudents = it.students.filter { uid -> uid != studentUID }
                val updatedGroup = it.copy(students = updatedStudents)
                groupDocument.set(updatedGroup)
                    .addOnSuccessListener {
                        Log.d(COMMON_DEBUG_TAG, "Student $studentUID removed from group $groupIdentifier successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.d(COMMON_DEBUG_TAG,"Error removing student $studentUID from group $groupIdentifier: $e")
                    }
            }
        } else {
            Log.d(COMMON_DEBUG_TAG,"Group $groupIdentifier does not exist.")
        }
    }.addOnFailureListener { e ->
        Log.d(COMMON_DEBUG_TAG,"Error getting group document: $e")
    }
}
}