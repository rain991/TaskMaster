package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.viewModels.auth.LoginScreenViewModel
import com.example.taskmaster.data.viewModels.auth.RegisterScreenViewModel
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.domain.useCases.common.LoginUseCase
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { Firebase.firestore }

    single<LoginRepositoryImpl> { LoginRepositoryImpl(get(), get()) }
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl(get(), get()) }
}

val domainModule = module {
    single<RegisterUseCase> { RegisterUseCase(get()) }
    single<LoginUseCase> { LoginUseCase(get(), get()) }
}

val viewModelModule = module {
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { ScreenManagerViewModel() }
}
