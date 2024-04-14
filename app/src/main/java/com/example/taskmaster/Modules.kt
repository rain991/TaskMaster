package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.implementations.core.other.PersonRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.AddTaskRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.GroupRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.GroupsListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.SearchRepositoryImpl
import com.example.taskmaster.data.viewModels.auth.LoginScreenViewModel
import com.example.taskmaster.data.viewModels.auth.RegisterScreenViewModel
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.CreateGroupViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupListScreenViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.CreateTaskViewModel
import com.example.taskmaster.domain.useCases.common.LoginUseCase
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.example.taskmaster.domain.useCases.teacher.group.CreateGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.group.DeleteGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.group.DeletePersonFromGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.tasks.CreateTaskUseCase
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

    single<AddTaskRepositoryImpl> { AddTaskRepositoryImpl(get()) }

    single<GroupsListRepositoryImpl> { GroupsListRepositoryImpl(get()) }
    single<GroupRepositoryImpl> { GroupRepositoryImpl(get()) }

    single<SearchRepositoryImpl> { SearchRepositoryImpl(get()) }
    single<PersonRepositoryImpl> { PersonRepositoryImpl(get()) }
}

val domainModule = module {
    // Auth
    single<RegisterUseCase> { RegisterUseCase(get()) }
    single<LoginUseCase> { LoginUseCase(get(), get()) }

    //Teacher
    single<CreateTaskUseCase> { CreateTaskUseCase(get()) }
    single<CreateGroupUseCase> { CreateGroupUseCase(get()) }
    single<DeleteGroupUseCase> { DeleteGroupUseCase(get()) }
    single<DeletePersonFromGroupUseCase> { DeletePersonFromGroupUseCase(get(), get()) }
}

val viewModelModule = module {
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { ScreenManagerViewModel() }
    viewModel { CreateTaskViewModel() }
    viewModel { GroupListScreenViewModel(get(), get(), get()) }
    viewModel { CreateGroupViewModel(get(), get(), get()) }
    viewModel { GroupDetailedScreenViewModel(get(), get()) }
}
