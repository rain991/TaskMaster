package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.viewModels.RegisterScreenViewModel
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseDatabase> { FirebaseDatabase.getInstance() }

    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl() }

}

val domainModule = module {
    single<RegisterUseCase> { RegisterUseCase(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { RegisterScreenViewModel(get()) }
}
