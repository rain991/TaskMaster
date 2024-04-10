package com.example.taskmaster.data.viewModels.teacher

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.taskmaster.data.models.entities.Group

class GroupsScreenViewModel : ViewModel() {

    private val _groupsList = mutableStateListOf<Group>()
    val groupsList: List<Group> = _groupsList





    fun deleteGroup(){

    }
}