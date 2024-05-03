package com.example.taskmaster.data.viewModels.student.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.R
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.student.AddToGroupByIdentifierUseCase
import com.example.taskmaster.presentation.UiText.UiText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentGroupViewModel(
    private val studentGroupListRepositoryImpl: StudentGroupListRepositoryImpl,
    private val teacherTaskRepositoryImpl: TeacherTaskRepositoryImpl,
    private val addToGroupByIdentifierUseCase: AddToGroupByIdentifierUseCase,
    auth: FirebaseAuth
) : ViewModel() {
    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList

    private val _teacherNameMap = mutableStateMapOf<String, String>()
    val teacherNameMap: Map<String, String> = _teacherNameMap

    private val _warningMessage = MutableStateFlow<UiText?>(null)
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

    private fun setCurrentWarningMessage(warningMessage: UiText?) {
        _warningMessage.value = warningMessage
    }

    suspend fun fetchTeacherNames() {
        val teacherUids = _groupsList.map { it.teacher }.distinct()
        teacherUids.forEach { uid ->
            val teacherName = teacherTaskRepositoryImpl.getTeacherNameByUid(uid)
            _teacherNameMap[uid] = teacherName
        }
    }

    suspend fun addToGroupByIdentifier(groupIdentifier: String) {
        if (groupIdentifier.length == 20 && currentFirebaseUser?.email != null) {
            addToGroupByIdentifierUseCase(currentFirebaseUser.email!!, groupIdentifier)
        } else {
            setCurrentWarningMessage(UiText(R.string.student_groups_group_identifier_error))
        }
    }

    private fun setGroupsList(listOfRelatedGroups: List<Group>) {
        _groupsList.clear()
        _groupsList.addAll(listOfRelatedGroups)
    }
}