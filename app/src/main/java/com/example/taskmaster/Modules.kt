package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.viewModels.RegisterScreenViewModel
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

    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl(get(), get()) }

}

val domainModule = module {
    single<RegisterUseCase> { RegisterUseCase(get()) }
}

val viewModelModule = module {
    viewModel { RegisterScreenViewModel(get()) }
}
