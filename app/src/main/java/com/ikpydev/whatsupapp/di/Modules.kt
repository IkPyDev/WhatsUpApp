package com.ikpydev.whatsupapp.di

import com.github.terrakok.cicerone.Cicerone
import com.ikpydev.data.local.settings.SettingsRealm
import com.ikpydev.data.local.settings.SettingsStore
import com.ikpydev.data.local.settings.SettingsStoreImpl
import com.ikpydev.data.local.user.UserStore
import com.ikpydev.data.local.user.UserStoreImpl
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.auth.AuthFirebaseImpl
import com.ikpydev.data.remote.messages.MessageFirebaseImpl
import com.ikpydev.data.remote.messages.MessagesFireBase
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.data.remote.users.UsersFireStoreImpl
import com.ikpydev.data.repo.AuthRepositoryImpl
import com.ikpydev.data.repo.ChatRepositoryImpl
import com.ikpydev.data.repo.SettingsRepositoryImpl
import com.ikpydev.domain.model.ActivityHolder
import com.ikpydev.domain.repo.AuthRepository
import com.ikpydev.domain.repo.ChatRepository
import com.ikpydev.domain.repo.SettingsRepository
import com.ikpydev.domain.usecase.auth.SendSmsCodeUseCase
import com.ikpydev.domain.usecase.auth.VerifyCodeUseCase
import com.ikpydev.domain.usecase.chat.GetChatsUseCase
import com.ikpydev.domain.usecase.chat.GetMessageUseCase
import com.ikpydev.domain.usecase.chat.SendMessageUseCase
import com.ikpydev.domain.usecase.settings.GetInitialScreenUseCase
import com.ikpydev.domain.usecase.settings.OnboardedUseCase
import com.ikpydev.presentation.screens.chat.ChatViewModel
import com.ikpydev.presentation.screens.code.CodeViewModel
import com.ikpydev.presentation.screens.home.HomeViewModel
import com.ikpydev.presentation.screens.main.MainViewModel
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel
import com.ikpydev.presentation.screens.phone.PhoneViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val cicerone = Cicerone.create()

val config = RealmConfiguration.create(schema = setOf(SettingsRealm::class))

val appModule = module {
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
    single { Realm.open(config) }
    single { ActivityHolder() }
}

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get()) }
}


val useCaseModule = module {
    single { SendSmsCodeUseCase(get()) }

    single { OnboardedUseCase(get()) }
    single { GetInitialScreenUseCase(get(), get()) }
    single { VerifyCodeUseCase(get()) }
    single { GetChatsUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { GetMessageUseCase(get()) }
}

val localModel = module {
    single<UserStore> { UserStoreImpl() }
    single<SettingsStore> { SettingsStoreImpl(get()) }
}

val remoteModule = module {
    single<AuthFirebase> { AuthFirebaseImpl(get()) }
    single<UsersFireStore> { UsersFireStoreImpl(get()) }
    single<MessagesFireBase> { MessageFirebaseImpl() }
}

val viewModel = module {
    viewModel { PhoneViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { OnboardedViewModel(get(), get()) }
    viewModel { CodeViewModel(get(), get()) }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { ChatViewModel(get(), get()) }
}

