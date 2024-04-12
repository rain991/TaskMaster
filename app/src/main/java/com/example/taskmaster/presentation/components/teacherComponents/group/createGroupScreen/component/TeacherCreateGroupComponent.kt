package com.example.taskmaster.presentation.components.teacherComponents.group.createGroupScreen.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.data.viewModels.teacher.groups.CreateGroupViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherCreateGroupComponent() {
    val viewModel = koinViewModel<CreateGroupViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current
    val lazyListState = rememberLazyListState()
    val addedStudentsList = viewModel.addedStudentsList
    val searchStudentsList = viewModel.searchedStudentsList
    val searchText = viewModel.searchText.collectAsState()
    val warningMessage = viewModel.warningMessage.collectAsState()
    val groupName = viewModel.groupNameText.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Create group", style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(32.dp))
        GradientInputTextField(value = groupName.value, label = "Group name") {
            viewModel.setGroupName(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .height(120.dp),
            query = searchText.value,
            onQueryChange = { viewModel.setSearchText(it) },
            onSearch = {
                if (it.length > 2) {
                    coroutineScope.launch {
                        viewModel.searchStudent(it)
                    }
                }
            },
            placeholder = {
                Text(text = "Search new students")
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
                if (searchText.value.length > 2) {
                    LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                        items(searchStudentsList.size) { index ->
                            val currentItem = searchStudentsList[index]
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.addStudentFromSearchList(currentItem) }) {
                                Text(text = currentItem.name)
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                        }
                    }
                }
            },
            active = true,
            onActiveChange = {},
            tonalElevation = 0.dp
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!addedStudentsList.isEmpty()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(text = "Added students", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
                items(
                    if (searchText.value.length > 2) {
                        searchStudentsList.size
                    } else {
                        addedStudentsList.size
                    }
                ) { index ->
                    val currentItem = if (searchText.value.length > 2) {
                        searchStudentsList[index]
                    } else {
                        addedStudentsList[index]
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.addStudentFromSearchList(currentItem) }) {
                        Text(text = currentItem.name)
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { viewModel.createGroup() }) {
                Text(text = "Create group")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (warningMessage.value != "") {
            Toast.makeText(localContext, warningMessage.value, Toast.LENGTH_SHORT).show()
            viewModel.deleteWarningMessage()
        }
    }
}