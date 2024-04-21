package com.example.taskmaster

import com.example.taskmaster.data.implementations.auth.LoginRepositoryImpl
import com.example.taskmaster.data.implementations.auth.RegisterRepositoryImpl
import com.example.taskmaster.data.implementations.core.other.PersonRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupListRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.groups.StudentGroupRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.tasks.StudentTaskListRepositoryImpl
import com.example.taskmaster.data.implementations.core.student.tasks.StudentTaskRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.groups.TeacherGroupsListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.other.TeacherSearchRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherAddTaskRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskListRepositoryImpl
import com.example.taskmaster.data.implementations.core.teacher.tasks.TeacherTaskRepositoryImpl
import com.example.taskmaster.data.viewModels.auth.LoginScreenViewModel
import com.example.taskmaster.data.viewModels.auth.RegisterScreenViewModel
import com.example.taskmaster.data.viewModels.other.FileSelectorViewModel
import com.example.taskmaster.data.viewModels.other.ListenersManagerViewModel
import com.example.taskmaster.data.viewModels.other.ScreenManagerViewModel
import com.example.taskmaster.data.viewModels.student.groups.StudentGroupScreenViewModel
import com.example.taskmaster.data.viewModels.student.tasks.StudentTasksViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.CreateGroupViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.GroupDetailedScreenViewModel
import com.example.taskmaster.data.viewModels.teacher.groups.TeacherGroupListScreenViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.CreateTaskViewModel
import com.example.taskmaster.data.viewModels.teacher.tasks.TeacherTaskListViewModel
import com.example.taskmaster.domain.useCases.common.LoginUseCase
import com.example.taskmaster.domain.useCases.common.RegisterUseCase
import com.example.taskmaster.domain.useCases.student.AddToGroupByIdentifierUseCase
import com.example.taskmaster.domain.useCases.teacher.group.CreateGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.group.DeleteGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.group.DeletePersonFromGroupUseCase
import com.example.taskmaster.domain.useCases.teacher.tasks.CreateTaskUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // App and Firebase
    single<App> { App() }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    single<FirebaseFirestore> { Firebase.firestore }
    single<FirebaseStorage> { FirebaseStorage.getInstance() }
    // Auth
    single<LoginRepositoryImpl> { LoginRepositoryImpl(get(), get()) }
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl(get(), get()) }
    // Teacher
    single<TeacherAddTaskRepositoryImpl> { TeacherAddTaskRepositoryImpl(get(), get()) }
    single<TeacherTaskListRepositoryImpl> { TeacherTaskListRepositoryImpl(get(), get()) }
    single<TeacherTaskRepositoryImpl> { TeacherTaskRepositoryImpl(get()) }

    single<TeacherGroupsListRepositoryImpl> { TeacherGroupsListRepositoryImpl(get(), get()) }
    single<TeacherGroupRepositoryImpl> { TeacherGroupRepositoryImpl(get()) }

    single<TeacherSearchRepositoryImpl> { TeacherSearchRepositoryImpl(get()) }
    // Student
    single<StudentGroupListRepositoryImpl> { StudentGroupListRepositoryImpl(get(), get()) }
    single<StudentGroupRepositoryImpl> { StudentGroupRepositoryImpl(get()) }

    single<StudentTaskRepositoryImpl> { StudentTaskRepositoryImpl() }
    single<StudentTaskListRepositoryImpl> { StudentTaskListRepositoryImpl(get(), get()) }
    // Common
    single<PersonRepositoryImpl> { PersonRepositoryImpl(get()) }
}

val domainModule = module {
    // Auth
    single<RegisterUseCase> { RegisterUseCase(get()) }
    single<LoginUseCase> { LoginUseCase(get(), get()) }

    // Student
    single<AddToGroupByIdentifierUseCase> { AddToGroupByIdentifierUseCase(get()) }

    // Teacher
    single<CreateTaskUseCase> { CreateTaskUseCase(get()) }
    single<CreateGroupUseCase> { CreateGroupUseCase(get()) }
    single<DeleteGroupUseCase> { DeleteGroupUseCase(get()) }
    single<DeletePersonFromGroupUseCase> { DeletePersonFromGroupUseCase(get()) }
}

val viewModelModule = module {
    //Auth
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get(), get()) }
    viewModel { ListenersManagerViewModel(get(), get()) }

    // Common
    viewModel { ScreenManagerViewModel(get(), get()) }
    viewModel { FileSelectorViewModel() }

    // Student
    viewModel { StudentGroupScreenViewModel(get(), get(), get(), get()) }
    viewModel { StudentTasksViewModel(get(), get(), get(), get()) }

    // Teacher
    viewModel { TeacherGroupListScreenViewModel(get(), get(), get()) }
    viewModel { TeacherTaskListViewModel(get(), get(), get()) }
    viewModel { CreateGroupViewModel(get(), get(), get()) }
    viewModel { GroupDetailedScreenViewModel(get(), get()) }
    viewModel { CreateTaskViewModel(get(), get(), get()) }
}
