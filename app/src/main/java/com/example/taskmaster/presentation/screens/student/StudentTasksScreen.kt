package com.example.taskmaster.presentation.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterScreenHeader

@Composable
fun StudentTasksScreen() {
    val bottomBarNavigationItems = listOf(NavigationItem.TaskScreen, NavigationItem.FinishedScreen, NavigationItem.GroupScreen)

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TaskMasterScreenHeader(isTeacherScreen = false, userName = "") // VM params
        }, bottomBar = { /*TaskMasterBottomBar(items = bottomBarNavigationItems, selectedItem =)*/ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            // TaskMasterSearchBar(searchText =, onSearchTextChange =, onSearch =, isSearching =)

        }
    }
}