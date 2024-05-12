package com.example.taskmaster.presentation.components.common.loginComponents

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.components.validateEmail
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import com.google.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject

@Composable
fun ResetPasswordComponent(paddingValues: PaddingValues, navController: NavController) {
    val localContext = LocalContext.current
    val auth = koinInject<FirebaseAuth>()
    var email by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var resetPasswordResult by remember { mutableStateOf<ResetPasswordResult<String>>(ResetPasswordResult.Success("")) }

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
        GradientInputTextField(value = email, label = "Email") {
            email = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validateEmail(email)) {
                    loading = true
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            loading = false
                            resetPasswordResult = if (task.isSuccessful) {
                                navController.navigate(Screen.LoginScreen.route)
                                ResetPasswordResult.Success("Password reset email sent successfully")
                            } else {
                                ResetPasswordResult.Error("Failed to send reset password email: ${task.exception?.message}")
                            }
                        }
                } else {
                    Toast.makeText(localContext, localContext.getString(R.string.reset_password_password_error), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(0.36f)
        ) {
            Text(text = stringResource(id = R.string.login_reset_password), textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
            Text(text = stringResource(id = R.string.answer_detailed_screen_go_back))
        }
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

sealed class ResetPasswordResult<out T> {
    data class Success<out T>(val data: T) : ResetPasswordResult<T>()
    data class Error(val message: String) : ResetPasswordResult<Nothing>()
}