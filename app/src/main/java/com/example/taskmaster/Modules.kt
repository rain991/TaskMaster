package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl() }
}

val domainModule = module {

}

val viewModelModule = module {

}
