package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.navigation.Navigation
import com.example.taskmaster.presentation.screens.common.ScreenPlaceholder
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by inject()
    private val loginRepositoryImpl: LoginRepositoryImpl by inject()
    private lateinit var authStateListener: AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutineScope = rememberCoroutineScope()
            var isLogined by remember { mutableStateOf(false) }
            var currentUserType by remember { mutableStateOf<UserTypes?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            if (isLoading) {
                ScreenPlaceholder()
            } else {
                TaskMasterTheme {
                    Navigation(
                        isLogined = isLogined,
                        startDestination = if (currentUserType != null) {
                            Screen.TaskScreen.route
                        } else {
                            Screen.LoginScreen.route
                        },
                        currentUserType = currentUserType
                    )
                }
            }
            LaunchedEffect(key1 = Unit) {
                auth.addAuthStateListener { state ->
                    isLoading = true
                    val currentUserUID = state.currentUser?.uid
                    if (currentUserUID == null) {
                        isLogined = false
                        currentUserType = null
                        isLoading = false
                    } else {
                        coroutineScope.launch {
                            currentUserType = when (loginRepositoryImpl.getCurrentUserType()) {
                                UserTypes.Student.name -> UserTypes.Student
                                UserTypes.Teacher.name -> UserTypes.Teacher
                                else -> null
                            }
                            isLoading = false
                        }
                    }
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
     //   auth.removeAuthStateListener(authStateListener)
    }
}