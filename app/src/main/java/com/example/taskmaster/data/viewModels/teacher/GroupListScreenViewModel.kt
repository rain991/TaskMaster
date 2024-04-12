package com.example.taskmaster.data.viewModels.teacher

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.GroupsListRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.domain.useCases.teacher.group.DeleteGroupUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupListScreenViewModel(
    private val groupsListRepositoryImpl: GroupsListRepositoryImpl,
    private val auth: FirebaseAuth,
    private val deleteGroupUseCase: DeleteGroupUseCase
) : ViewModel() {

    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList

    private val _groupToDelete = MutableStateFlow<Group?>(null)
    val groupToDelete = _groupToDelete.asStateFlow()

    init {
        viewModelScope.launch {
            auth.currentUser?.let { firebaseUser ->
                groupsListRepositoryImpl.getGroupsRelatedToTeacher(firebaseUser.uid).collect {
                    _groupsList.clear()
                    _groupsList.addAll(it)
                }
            }
        }
    }

    fun deleteGroup(groupIdentifier: String) {
        deleteGroupUseCase(groupIdentifier)
    }

    fun setIsDeleteDialogShown(value: Group?) {
        _groupToDelete.value = value
    }
}