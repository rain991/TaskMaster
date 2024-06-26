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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.data.viewModels.auth.RegisterScreenViewModel
import com.example.taskmaster.presentation.components.common.textfields.GradientInputTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenComponent(paddingValues: PaddingValues, navController: NavController) {
    val viewModel = koinViewModel<RegisterScreenViewModel>()
    val screenState = viewModel.registerScreenState.collectAsState()
    val registerOptions = listOf(UserTypes.Student, UserTypes.Teacher)
    val localContext = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier.size(220.dp)) {
            Image(
                painter = painterResource(id = R.drawable.appicon1),
                contentDescription = stringResource(R.string.app_icon),
                modifier = Modifier.size(220.dp)
            )
        }
        Text(text = stringResource(R.string.task_master), style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(0.64f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.i_am), style = MaterialTheme.typography.titleSmall)
            SingleChoiceSegmentedButtonRow {
                registerOptions.forEachIndexed { index, type ->
                    SegmentedButton(
                        modifier = Modifier.safeContentPadding(),
                        onClick = { viewModel.setUserType(type) },
                        selected = screenState.value.userType == type,
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = registerOptions.size)
                    ) {
                        val buttonText = when (type) {
                            UserTypes.Student -> stringResource(R.string.student)
                            UserTypes.Teacher -> stringResource(R.string.teacher)
                        }

                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            text = buttonText,
                            maxLines = 1,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        GradientInputTextField(value = screenState.value.name, label = stringResource(R.string.name_label)) {
            viewModel.setName(it)
        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(value = screenState.value.surname, label = stringResource(R.string.surname_label)) {
            viewModel.setSurname(it)
        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(value = screenState.value.email, label = stringResource(R.string.email_label)) {
            viewModel.setEmail(it)
        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(value = screenState.value.password, label = stringResource(R.string.password_label), keyboardType = KeyboardType.Password) {
            viewModel.setPassword(it)
        }
        Spacer(modifier = Modifier.height(12.dp))
        GradientInputTextField(value = screenState.value.repeatPassword, label = stringResource(R.string.repeat_password_label), keyboardType = KeyboardType.Password) {
            viewModel.setRepeatPassword(it)
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = { navController.navigate(Screen.LoginScreen.route) }) {
            Text(text = stringResource(R.string.register_screen_have_account_button))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.tryRegisterNewUser() }) {
            Row(modifier = Modifier.fillMaxWidth(0.36f), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.register_button))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        if(screenState.value.warningMessage !=null){
            Toast.makeText(LocalContext.current, screenState.value.warningMessage!!.asString(localContext), Toast.LENGTH_SHORT).show()
            viewModel.deleteWarningMessage()
        }
    }
}
