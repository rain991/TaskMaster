package com.example.taskmaster.data.viewModels.teacher.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupsListRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.teacher.group.DeleteGroupUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherGroupListScreenViewModel(
    private val groupsListRepositoryImpl: TeacherGroupsListRepositoryImpl,
    private val auth: FirebaseAuth,
    private val deleteGroupUseCase: DeleteGroupUseCase
) : ViewModel() {

    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList

    private val _groupToDelete = MutableStateFlow<Group?>(null)
    val groupToDelete = _groupToDelete.asStateFlow()

    private val currentFirebaseUser = auth.currentUser

    init {
        viewModelScope.launch {
            groupsListRepositoryImpl.getTeacherGroups(currentFirebaseUser!!.uid).collect {
                setGroupsList(it)
            }
        }
    }
    fun deleteGroup(groupIdentifier: String) {
        deleteGroupUseCase(groupIdentifier)
    }

    fun setIsDeleteDialogShown(value: Group?) {
        _groupToDelete.value = value
    }

    suspend fun fetchCurrentGroups() {
        setGroupsList(groupsListRepositoryImpl.getGroupsRelatedToTeacher(currentFirebaseUser!!.uid))
    }

    private fun setGroupsList(listOfRelatedGroups: List<Group>) {
        _groupsList.clear()
        _groupsList.addAll(listOfRelatedGroups)
    }
}