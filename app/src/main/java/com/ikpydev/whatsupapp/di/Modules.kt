package com.ikpydev.whatsupapp.di

import com.github.terrakok.cicerone.Cicerone
import com.ikpydev.data.local.user.UserStore
import com.ikpydev.data.local.user.UserStoreImpl
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.auth.AuthFirebaseImpl
import com.ikpydev.data.repo.AuthRepositoryImpl
import com.ikpydev.domain.repo.AuthRepository
import com.ikpydev.domain.usecase.auth.SendSmsCodeUseCase
import com.ikpydev.presentation.screens.main.MainViewModel
import com.ikpydev.presentation.screens.phone.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val cicerone = Cicerone.create()

val appModule = module {
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }
}


val useCaseModule = module {
    single { SendSmsCodeUseCase(get()) }
}

val localModel = module {
    single<UserStore> { UserStoreImpl() }
}

val remoteModule = module {
    single<AuthFirebase> { AuthFirebaseImpl() }
}

val viewModel = module {
    viewModel { PhoneViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

