package com.example.taskmaster.data.viewModels.student.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class StudentGroupScreenViewModel(
    private val studentGroupListRepositoryImpl: StudentGroupListRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    auth: FirebaseAuth
) : ViewModel() {
    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList

    private val _teacherNameMap = mutableStateMapOf<String, String>()
    val teacherNameMap: Map<String, String> = _teacherNameMap

    private val currentFirebaseUser = auth.currentUser

    init {
        viewModelScope.launch {
            if (currentFirebaseUser?.email != null) {
                studentGroupListRepositoryImpl.getStudentGroups(currentFirebaseUser.email!!).collect {
                    setGroupsList(it)
                }
                fetchTeacherNames()
            }
        }
    }


    private fun fetchTeacherNames() {
        viewModelScope.launch {
            val teacherUids = groupsList.map { it.teacher }.distinct()
            teacherUids.forEach { uid ->
                val teacherName = teacherTaskRepositoryImpl.getTeacherNameByUid(uid)
                _teacherNameMap[uid] = teacherName
            }
        }
    }

    suspend fun getTeacherNameByUid(uid: String): String {
        return teacherTaskRepositoryImpl.getTeacherNameByUid(uid)
    }

    suspend fun addToGroupByIdentifier(groupIdentifier: String) {

    }

    private fun setGroupsList(listOfRelatedGroups: List<Group>) {
        _groupsList.clear()
        _groupsList.addAll(listOfRelatedGroups)
    }

}