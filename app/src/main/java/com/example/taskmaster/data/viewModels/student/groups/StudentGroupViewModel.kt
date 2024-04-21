package com.example.taskmaster.data.viewModels.student.groups

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.student.AddToGroupByIdentifierUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentGroupScreenViewModel(
    private val studentGroupListRepositoryImpl: StudentGroupListRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    private val addToGroupByIdentifierUseCase: AddToGroupByIdentifierUseCase,
    auth: FirebaseAuth
) : ViewModel() {
    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList

    private val _teacherNameMap = mutableStateMapOf<String, String>()
    val teacherNameMap: Map<String, String> = _teacherNameMap

    private val _warningMessage = MutableStateFlow<String?>(null)
    val warningMessage = _warningMessage.asStateFlow()

    private val currentFirebaseUser = auth.currentUser

    init {
        viewModelScope.launch {
            if (currentFirebaseUser?.email != null) {
                studentGroupListRepositoryImpl.getStudentGroups(currentFirebaseUser.email!!).collect {
                    setGroupsList(it)
                    fetchTeacherNames()
                }

            }
        }
    }

    fun deleteCurrentWarningMessage() {
        _warningMessage.value = null
    }

    private fun setCurrentWarningMessage(warningMessage: String) {
        _warningMessage.value = warningMessage
    }

    suspend fun fetchTeacherNames() {
        val teacherUids = _groupsList.map { it.teacher }.distinct()
        Log.d(COMMON_DEBUG_TAG, "fetchTeacherNames: groups list size : ${_groupsList.size} ")
        Log.d(COMMON_DEBUG_TAG, "fetchTeacherNames: teacherUids size : ${teacherUids.size} ")
        teacherUids.forEach { uid ->
            val teacherName = teacherTaskRepositoryImpl.getTeacherNameByUid(uid)
            _teacherNameMap[uid] = teacherName
        }
    }

    suspend fun addToGroupByIdentifier(groupIdentifier: String) {
        if (groupIdentifier.length == 20 && currentFirebaseUser?.email != null) {
            addToGroupByIdentifierUseCase(currentFirebaseUser.email!!, groupIdentifier)
        } else {
            setCurrentWarningMessage("Group identifier must be 20 characters long")
        }
    }

    private fun setGroupsList(listOfRelatedGroups: List<Group>) {
        _groupsList.clear()
        _groupsList.addAll(listOfRelatedGroups)
        Log.d(COMMON_DEBUG_TAG, "fetchTeacherNames: groups list size : ${listOfRelatedGroups.size} ")
    }
}