package com.example.taskmaster

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.Level

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            // workManagerFactory()
            modules(listOf(firebaseModule, appModule, domainModule, viewModelModule))
        }
    }
    fun reinitializeKoinFirebaseModule() {
        unloadKoinModules(listOf(firebaseModule))
        loadKoinModules(listOf(firebaseModule))
    }
}