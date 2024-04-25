package com.example.taskmaster

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.taskmaster.data.constants.COMMON_DEBUG_TAG
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
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by inject()
    private val loginRepositoryImpl: LoginRepositoryImpl by inject()
    private lateinit var authStateListener: AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val app = koinInject<App>()
            val coroutineScope = rememberCoroutineScope()
            var isLogined by remember { mutableStateOf(false) }
            var currentUserType by remember { mutableStateOf<UserTypes?>(null) }
            var isLoading by remember { mutableStateOf(true) }

            if (isLoading) {
                ScreenPlaceholder()
                Log.d(COMMON_DEBUG_TAG, "onCreate: SCREEN PLACEHOLDER CALLED")
                Log.d(COMMON_DEBUG_TAG, "onCreate: current user type  (placeholder): ${currentUserType?.name}")
            } else {
                Log.d(COMMON_DEBUG_TAG, "onCreate: current user type : ${currentUserType?.name}")
                Log.d(COMMON_DEBUG_TAG, "onCreate: user uid : ${auth.currentUser?.uid}")
                Log.d(COMMON_DEBUG_TAG, "onCreate: NAVIGATION CALLED")
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
            auth.addAuthStateListener { state ->
                Log.d(COMMON_DEBUG_TAG, "onCreate: auth state changed")
                val currentUserUID = state.currentUser?.uid
                if(currentUserUID == null){
                    isLogined = false
                    currentUserType = null
                }else {
                    coroutineScope.launch {
                        currentUserType = when (loginRepositoryImpl.getCurrentUserType()) {
                            UserTypes.Student.name -> UserTypes.Student
                            UserTypes.Teacher.name -> UserTypes.Teacher
                            else -> null
                        }
                        Log.d(COMMON_DEBUG_TAG, "onCreate: user type fetched : $currentUserType")
                    }
                }
                isLoading = false
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