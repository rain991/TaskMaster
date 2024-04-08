package com.example.taskmaster

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.models.entities.UserTypes
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.navigation.Navigation
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginRepositoryImpl: LoginRepositoryImpl by inject()
        setContent {
            var isLogined by remember { mutableStateOf(false) }
            var currentUserType by remember { mutableStateOf<UserTypes?>(null) }
            auth.addAuthStateListener { state ->
                val currentUser = state.currentUser?.uid
                if (currentUser != null) {
                    isLogined = true
                } else {
                    isLogined = false
                    currentUserType = null
                }
            }
            LaunchedEffect(key1 = isLogined) {
                currentUserType = when (loginRepositoryImpl.getCurrentUserType()) {
                    UserTypes.Student.name -> UserTypes.Student
                    UserTypes.Teacher.name -> UserTypes.Teacher
                    else -> {
                        UserTypes.Student
                    }
                }
            }
            Log.d(COMMON_DEBUG_TAG, "onCreate: userType:${currentUserType}}")
            TaskMasterTheme {
                Navigation(
                    isLogined = isLogined, startDestination = if (isLogined) {
                        Screen.TaskScreen.route
                    } else {
                        Screen.LoginScreen.route
                    }, currentUserType = currentUserType
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        //  auth.removeAuthStateListener()
    }
}