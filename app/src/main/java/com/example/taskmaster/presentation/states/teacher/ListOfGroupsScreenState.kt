package com.example.taskmaster.presentation.states.teacher

import com.example.taskmaster.data.models.entities.Group

data class ListOfGroupsScreenState( // currently not used
    val listOfGroups: List<Group>,
    val isDeleteDialogShown: Boolean,
    val deleteDialogGroup: Group? = null
)
