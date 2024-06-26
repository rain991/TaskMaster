package com.example.taskmaster.presentation.components.common.loginComponents

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.auth.LoginScreenViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenComponent(paddingValues: PaddingValues, navController: NavController) {
    val viewModel = koinViewModel<LoginScreenViewModel>()
    val screenState = viewModel.screenState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.size(220.dp)) {
            Image(
                painter = painterResource(id = R.drawable.appicon1),
                contentDescription = stringResource(R.string.app_icon),
                modifier = Modifier.size(220.dp)
            )
        }
        Text(text = stringResource(R.string.task_master), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        GradientInputTextField(value = screenState.value.email, label = stringResource(id = R.string.email_label)) {
            viewModel.setEmail(it)
        }
        Spacer(modifier = Modifier.height(8.dp))
        GradientInputTextField(
            value = screenState.value.password,
            label = stringResource(R.string.password_label),
            keyboardType = KeyboardType.Password
        ) {
            viewModel.setPassword(it)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                viewModel.login(navController)
            }
        }) {
            Row(modifier = Modifier.fillMaxWidth(0.36f), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.login))
            }
        }
        TextButton(onClick = { navController.navigate(Screen.ResetPasswordScreen.route) }) {
            Text(text = stringResource(R.string.login_reset_password))
        }
        TextButton(onClick = { navController.navigate(Screen.RegisterScreen.route) }) {
            Text(text = stringResource(R.string.login_no_account_yet))
        }
        Spacer(modifier = Modifier.weight(1f))
        if (screenState.value.warningMessage != null) {
            Toast.makeText(LocalContext.current, screenState.value.warningMessage!!.asString(localContext), Toast.LENGTH_SHORT).show()
            viewModel.deleteWarningMessage()
        }
    }
}