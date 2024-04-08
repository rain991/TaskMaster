package com.example.taskmaster.presentation.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.data.components.converters.convertScreenToNavigationItem
import com.example.taskmaster.data.constants.DEFAULT_USER_NAME
import com.example.taskmaster.data.models.navigation.NavigationItem
import com.example.taskmaster.data.viewModels.ScreenManagerViewModel
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterBottomBar
import com.example.taskmaster.presentation.components.common.barsAndHeaders.TaskMasterScreenHeader
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun StudentGroupsScreen(navController : NavController) {
    val auth = koinInject<FirebaseAuth>()
    val currentUserName = auth.currentUser?.displayName
    val bottomBarNavigationItems = listOf(NavigationItem.TaskScreen, NavigationItem.FinishedScreen, NavigationItem.GroupScreen)
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val screenManagerState = screenManagerViewModel.currentScreenState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TaskMasterScreenHeader(isTeacherScreen = true, userName = currentUserName ?: DEFAULT_USER_NAME)
        }, bottomBar = {
            TaskMasterBottomBar(
                items = bottomBarNavigationItems,
                selectedItem = convertScreenToNavigationItem(screenManagerState.value),
                navController = navController
            )
        }
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