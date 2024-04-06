package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.taskmaster.data.models.navigation.Screen
import com.example.taskmaster.presentation.navigation.Navigation
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val auth: FirebaseAuth by inject()
        val database: FirebaseFirestore by inject()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        val userRef = userId?.let { database.collection("users").document(it) }
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                Navigation(isLogined = (currentUser != null), startDestination = Screen.LoginScreen.route, currentUserType = null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}