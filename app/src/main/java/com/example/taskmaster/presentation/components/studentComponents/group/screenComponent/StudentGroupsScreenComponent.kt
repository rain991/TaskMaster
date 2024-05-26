package com.example.taskmaster.presentation.components.studentComponents.group.screenComponent

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmaster.R
import com.example.taskmaster.data.viewModels.student.groups.StudentGroupViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import com.example.taskmaster.presentation.components.studentComponents.group.uiComponents.StudentGroupSingleComponent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun StudentGroupsScreenComponent() {
    val viewModel = koinViewModel<StudentGroupViewModel>()
    val lazyListState = rememberLazyListState()
    val groupList = viewModel.groupsList
    val teacherUidToNameMap = viewModel.teacherNameMap
    val coroutineScope = rememberCoroutineScope()
    val warningMessage = viewModel.warningMessage.collectAsState()
    val localContext = LocalContext.current
    LaunchedEffect(key1 = groupList) {
        viewModel.fetchTeacherNames()
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        if (groupList.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.student_group_no_groups_yet_message))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(R.string.student_group_additional_message))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(id = R.string.groups),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    state = lazyListState, modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .fillMaxWidth()
                ) {
                    items(groupList) {
                        StudentGroupSingleComponent(group = it, teacherName = teacherUidToNameMap[it.teacher] ?: "", onComponentClick = {

                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var inputIdentifier by remember {
                mutableStateOf("")
            }
            Row(

                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.student_group_assign_to_new_group), style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.64f), horizontalArrangement = Arrangement.Center
            ) {
                GradientInputTextField(value = inputIdentifier, label = stringResource(R.string.group_identifier)) {
                    inputIdentifier = it
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(visible = inputIdentifier.length > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            viewModel.addToGroupByIdentifier(inputIdentifier)
                        }
                    }) {
                        Text(text = stringResource(R.string.student_groups_add_to_group), style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
            if (inputIdentifier.length > 1) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        if (warningMessage.value != null) {
            Toast.makeText(localContext, warningMessage.value?.asString(localContext), Toast.LENGTH_SHORT).show()
            viewModel.deleteCurrentWarningMessage()
        }
    }
}