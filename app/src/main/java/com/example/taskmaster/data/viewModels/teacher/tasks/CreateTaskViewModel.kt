package com.example.taskmaster.data.viewModels.teacher.tasks

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupsListRepositoryImpl
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.data.models.entities.Task
import com.example.taskmaster.domain.useCases.teacher.tasks.CreateTaskUseCase
import com.example.taskmaster.presentation.states.teacher.CreateTaskScreenState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateTaskViewModel(
    private val createTaskUseCase: CreateTaskUseCase,
    private val groupsListRepositoryImpl: TeacherGroupsListRepositoryImpl,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _createTaskScreenState = MutableStateFlow(
        CreateTaskScreenState(
            title = "",
            description = "",
            listOfGroupNames = listOf(),
            listOfSelectedGroupNames = listOf(),
            selectedDate = null,
            attachedFiles = listOf<Uri>(),
            isGroupSelectorShown = false
        )
    )
    val createTaskScreenState = _createTaskScreenState.asStateFlow()

    private val teacherGroups = mutableListOf<Group>()

    init {
        viewModelScope.launch {
            val currentTeacherGroups = groupsListRepositoryImpl.getGroupsRelatedToTeacher(auth.currentUser!!.uid)
            setGroupNames(currentTeacherGroups.map { it.name })
            teacherGroups.addAll(currentTeacherGroups)
        }
    }

    suspend fun createTask(context: Context) {

        if (_createTaskScreenState.value.selectedDate != null && auth.currentUser != null && _createTaskScreenState.value.title != "") {

            if(_createTaskScreenState.value.description != "" || _createTaskScreenState.value.attachedFiles.isNotEmpty()){
                val selectedGroupIdentifiers = teacherGroups.filter { group ->
                    _createTaskScreenState.value.listOfSelectedGroupNames.contains(group.name)
                }.map { it.identifier }
                val currentTask = Task(
                    name = _createTaskScreenState.value.title,
                    description = _createTaskScreenState.value.description,
                    groups = selectedGroupIdentifiers,
                    teacher = auth.currentUser!!.uid,
                    relatedFilesURL = listOf(),
                    endDate = _createTaskScreenState.value.selectedDate!!
                )
                createTaskUseCase(task = currentTask, localUriFilesList = _createTaskScreenState.value.attachedFiles, context = context)
            }

        } else {
            if(_createTaskScreenState.value.title == ""){
                setWarningMessage("Incorrect task title")
            }
            if(_createTaskScreenState.value.description == "" || _createTaskScreenState.value.attachedFiles.isEmpty()){
                setWarningMessage("You should attach task files or add any task description")
            }
            if(_createTaskScreenState.value.selectedDate == null){
                setWarningMessage("Incorrect task deadline")
            }
        }
    }

    fun setTitle(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(title = value)
    }

    fun setDescription(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(description = value)
    }


    fun setSelectedDate(value: Long) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(selectedDate = value)
    }

    fun setAttachedFiles(value: List<Uri>) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = value)
    }

    fun setDatePickerState(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(datePickerState = value)
    }

    fun setTimePickerState(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(timePickerState = value)
    }

    fun setIsGroupSelectorShown(value: Boolean) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(isGroupSelectorShown = value)
    }

    fun setGroupAsSelected(groupName: String) {
        val listOfSelectedGroupNames = _createTaskScreenState.value.listOfSelectedGroupNames.toMutableList()
        if (!listOfSelectedGroupNames.contains(groupName)) {
            listOfSelectedGroupNames.add(groupName)
            _createTaskScreenState.value = createTaskScreenState.value.copy(listOfSelectedGroupNames = listOfSelectedGroupNames)
        }
    }

    fun setGroupAsUnselected(groupName: String) {
        val listOfSelectedNames = _createTaskScreenState.value.listOfSelectedGroupNames.toMutableList()
        if (listOfSelectedNames.contains(groupName)) {
            listOfSelectedNames.remove(groupName)
            _createTaskScreenState.value = createTaskScreenState.value.copy(listOfSelectedGroupNames = listOfSelectedNames)
        }
    }

    //    fun addURI (value : Uri){
//        val attachedFiles = _createTaskScreenState.value.attachedFiles.toMutableList()
//        if(!attachedFiles.contains(value)){
//            attachedFiles.add(value)
//        }
//        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = attachedFiles)
//    }
    fun deleteURI(value: Uri) {
        val attachedFiles = _createTaskScreenState.value.attachedFiles.toMutableList()
        attachedFiles.remove(value)
        _createTaskScreenState.value = createTaskScreenState.value.copy(attachedFiles = attachedFiles)
    }

    fun setWarningMessage(value: String) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(warningMessage = value)
    }

    fun deleteWarningMessage() {
        _createTaskScreenState.value = createTaskScreenState.value.copy(warningMessage = null)
    }

    private fun setGroupNames(value: List<String>) {
        _createTaskScreenState.value = createTaskScreenState.value.copy(listOfGroupNames = value)
    }
}