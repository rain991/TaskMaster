package com.example.taskmaster.presentation.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.App
import com.example.taskmaster.R
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.presentation.components.common.drawable.CircleWithText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.Locale

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = koinInject<FirebaseAuth>()
    val app = koinInject<App>()
    val listenersManagerViewModel = koinViewModel<ListenersManagerViewModel>()
    val currentUserDisplayName = auth.currentUser?.displayName
    val currentUserName = currentUserDisplayName?.substringBefore(" ")
    val currentUserSurname = currentUserDisplayName?.substringAfter(" ")
    val coroutineScope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.navigate(Screen.TaskScreen.route) }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back to main screen")
                }
                Image(
                    painter = painterResource(id = R.drawable.appicon1),
                    contentDescription = stringResource(R.string.app_icon),
                    modifier = Modifier.size(48.dp)
                )
            }
        }, bottomBar = { },
        floatingActionButton = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), horizontalArrangement = Arrangement.Center
            ) {
                if (currentUserSurname != null && currentUserName != null) {
                    CircleWithText(
                        text = (currentUserSurname.substring(0, 1)
                            .toUpperCase(Locale.ROOT) + currentUserName.substring(0, 1).toUpperCase(Locale.ROOT)),
                        modifier = Modifier.size(96.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (currentUserSurname != null) {
                    Text(text = currentUserSurname, style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (currentUserName != null) {
                    Text(text = currentUserName, style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (auth.currentUser != null) {
                    Button(onClick = {
                        coroutineScope.launch {
                            auth.signOut()
                        }
                    }) {
                        Text(text = "Sign out", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}    //   app.reinitializeKoin()
// listenersManagerViewModel.removeAllListenersAndTerminateDatabase()