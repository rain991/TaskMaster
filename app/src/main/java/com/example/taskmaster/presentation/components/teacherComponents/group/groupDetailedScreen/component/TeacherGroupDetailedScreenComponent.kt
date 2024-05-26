package com.example.taskmaster.presentation.components.teacherComponents.group.groupDetailedScreen.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherGroupDetailedScreenComponent(viewModel: GroupDetailedScreenViewModel) {
    val screenManagerViewModel = koinViewModel<ScreenManagerViewModel>()
    val lazyListState = rememberLazyListState()
    val localContext = LocalContext.current
    val currentDetailedGroup = viewModel.currentDetailedGroup.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val currentAppliabableState = viewModel.isAppliable.collectAsState()
    val warningMessage = viewModel.warningMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        screenManagerViewModel.setScreen(UserTypes.Teacher, Screen.GroupDetailedScreen)
        viewModel.fetchCurrentAppliableState()
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.setCurrentDetailedGroup(null)
            viewModel.clearStudentsList()
        }
    }
    if (currentDetailedGroup.value == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.group_detailed_screen_error1),
                style = MaterialTheme.typography.titleSmall
            )
        }
    } else {
        val listOfGroupStudents = viewModel.listOfGroupStudents
        val searchedStudentsList = viewModel.searchedStudentsList
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = currentDetailedGroup.value!!.name,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.group),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.teacher_group_detailed_is_appliable),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(checked = currentAppliabableState.value,
                    onCheckedChange = {
                        coroutineScope.launch {
                            viewModel.toggleAppliableState(it)
                            viewModel.fetchCurrentAppliableState()
                        }
                    })
            }
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
                    .wrapContentHeight(),
                query = searchText.value,
                onQueryChange = {
                    viewModel.setSearchText(it.trim())
                    viewModel.searchStudent()
                },
                onSearch = {
                    viewModel.searchStudent()
                },
                placeholder = {
                    Text(text = stringResource(R.string.group_detailed_search_students))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                trailingIcon = {},
                content = {
                    Spacer(modifier = Modifier.height(8.dp))
                    if (searchText.value.isNotEmpty()) {
                        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                            items(searchedStudentsList.size) { index ->
                                val currentStudent = searchedStudentsList[index]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = currentStudent)
                                    Button(onClick = {
                                        coroutineScope.launch {
                                            viewModel.deleteStudentFromGroup(currentStudent)
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = stringResource(
                                                R.string.group_detailed_delete_student_from_group_CD,
                                                currentStudent
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                            items(
                                listOfGroupStudents.size
                            ) { index ->
                                val currentStudent = listOfGroupStudents[index]
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = currentStudent)
                                    Button(onClick = {
                                        coroutineScope.launch {
                                            viewModel.deleteStudentFromGroup(currentStudent)
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = stringResource(
                                                R.string.group_detailed_delete_student_from_group_CD,
                                                currentStudent
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                active = true,
                onActiveChange = {},
                tonalElevation = 0.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (listOfGroupStudents.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.group_detailed_group_is_empty_message),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
    if (warningMessage.value != null) {
        Toast.makeText(
            localContext,
            warningMessage.value?.asString(localContext),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.deleteWarningMessage()
    }
}