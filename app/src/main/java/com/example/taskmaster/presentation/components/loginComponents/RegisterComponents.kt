package com.example.taskmaster.presentation.components.loginComponents

import android.text.Layout
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.presentation.components.common.GradientInputTextField

@Composable
fun RegisterScreenComponent(paddingValues: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.size(220.dp)){
            Image(painter = painterResource(id = R.drawable.appicon1), contentDescription = stringResource(R.string.app_icon), modifier = Modifier.size(220.dp))
        }
        Text(text = stringResource(R.string.task_master), style = MaterialTheme.typography.titleMedium)
      //  Spacer(modifier = Modifier.height(60.dp))
        Spacer(modifier = Modifier.weight(1f))
        GradientInputTextField(text = "mock", label = "Name") {

        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(text = "mock", label = "Surname") {

        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(text = "mock", label = "Email") {

        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(text = "mock", label = "Password", keyboardType = KeyboardType.Password) {

        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(text = "mock", label = "Repeat Password", keyboardType = KeyboardType.Password) {

        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/ }) {
            Row(modifier = Modifier.fillMaxWidth(0.36f), horizontalArrangement = Arrangement.Center) {
                Text(text = "Register")
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
