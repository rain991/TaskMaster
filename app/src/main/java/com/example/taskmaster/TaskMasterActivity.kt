package com.example.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.taskmaster.presentation.navigation.Navigation
import com.example.taskmaster.ui.theme.TaskMasterTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    //    private lateinit var auth: FirebaseAuth
//    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
        val auth: FirebaseAuth by inject()
        val database: FirebaseDatabase by inject()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        val userRef = userId?.let { FirebaseDatabase.getInstance().getReference("users").child(it) }
        super.onCreate(savedInstanceState)
        setContent {
            TaskMasterTheme {
                Navigation(isLogined = (currentUser != null), currentUserType = null)
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }
}