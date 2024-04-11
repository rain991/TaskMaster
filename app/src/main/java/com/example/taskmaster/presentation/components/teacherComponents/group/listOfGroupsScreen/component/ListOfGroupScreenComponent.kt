package com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.taskmaster.data.models.entities.Group
import com.example.taskmaster.presentation.components.teacherComponents.group.listOfGroupsScreen.screenContent.SingleGroupComponent

@Composable
fun ListOfGroupScreenComponent(listOfGroups : List<Group>) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(listOfGroups){
            SingleGroupComponent(group = it) {

            }
        }
    }
}