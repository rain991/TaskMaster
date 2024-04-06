package com.example.taskmaster.presentation.components.common.loginComponents

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField

@Composable
fun LoginScreenComponent(paddingValues: PaddingValues, navController: NavController) {
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
        GradientInputTextField(value = "mock", label = "mock") {

        }
        Spacer(modifier = Modifier.height(8.dp))
        GradientInputTextField(value = "mock", label = "mock", keyboardType = KeyboardType.Password) {

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /*TODO*/ }) {
            Row(modifier = Modifier.fillMaxWidth(0.36f), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.login))
            }
        }
        TextButton(onClick = { navController.navigate(Screen.RegisterScreen.route) }) {
            Text(text = "no account yet?")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}