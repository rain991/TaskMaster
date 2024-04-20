package com.example.taskmaster.data.viewModels.other

import androidx.lifecycle.ViewModel
import com.example.taskmaster.App
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class ListenersManagerViewModel(private val database: FirebaseFirestore, private val app: App) : ViewModel() {
    private val listeners = mutableListOf<ListenerRegistration>()

    fun addNewListener(listener: ListenerRegistration) {
        listeners.add(listener)
    }

    fun removeAllListeners() {
        listeners.forEach {
            it.remove()
        }
    }

    suspend fun removeAllListenersAndTerminateDatabase() {
        listeners.forEach {
            it.remove()

        }
        database.terminate().await()
        database.clearPersistence().await()
        app.reinitializeKoin()
    }
}