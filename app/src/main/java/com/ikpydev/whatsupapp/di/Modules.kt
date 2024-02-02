package com.ikpydev.whatsupapp.di

import com.android.volley.toolbox.Volley
import com.github.terrakok.cicerone.Cicerone
import com.ikpydev.data.local.settings.SettingsRealm
import com.ikpydev.data.local.settings.SettingsStore
import com.ikpydev.data.local.settings.SettingsStoreImpl
import com.ikpydev.data.local.user.UserStore
import com.ikpydev.data.local.user.UserStoreImpl
import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.auth.AuthFirebaseImpl
import com.ikpydev.data.remote.files.ImageStorage
import com.ikpydev.data.remote.files.ImageStorageImpl
import com.ikpydev.data.remote.files.VoiceStorage
import com.ikpydev.data.remote.files.VoiceStorageImpl
import com.ikpydev.data.remote.groups.GroupsFireStore
import com.ikpydev.data.remote.groups.GroupsFireStoreImpl
import com.ikpydev.data.remote.messageGroup.MessageFirebaseGroupImpl
import com.ikpydev.data.remote.messageGroup.MessagesFireBaseGroup
import com.ikpydev.data.remote.messages.MessageFirebaseImpl
import com.ikpydev.data.remote.messages.MessagesFireBase
import com.ikpydev.data.remote.push.PushVolley
import com.ikpydev.data.remote.push.PushVolleyImpl
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.data.remote.users.UsersFireStoreImpl
import com.ikpydev.data.repo.AuthRepositoryImpl
import com.ikpydev.data.repo.ChatRepositoryImpl
import com.ikpydev.data.repo.GroupsChatRepositoryImpl
import com.ikpydev.data.repo.SettingsRepositoryImpl
import com.ikpydev.domain.model.ActivityHolder
import com.ikpydev.domain.repo.AuthRepository
import com.ikpydev.domain.repo.ChatRepository
import com.ikpydev.domain.repo.GroupsChatRepository
import com.ikpydev.domain.repo.SettingsRepository
import com.ikpydev.domain.usecase.auth.SendSmsCodeUseCase
import com.ikpydev.domain.usecase.auth.VerifyCodeUseCase
import com.ikpydev.domain.usecase.chat.GetChatsUseCase
import com.ikpydev.domain.usecase.chat.GetMessageUseCase
import com.ikpydev.domain.usecase.chat.SendImageUseCase
import com.ikpydev.domain.usecase.chat.SendMessageUseCase
import com.ikpydev.domain.usecase.chat.SendVoiceUseCase
import com.ikpydev.domain.usecase.group.CreateGroupsUseCase
import com.ikpydev.domain.usecase.group.GetGroupsChatsUseCase
import com.ikpydev.domain.usecase.group.GetGroupsMessagesUseCase
import com.ikpydev.domain.usecase.group.SendGroupsImageMessagesUseCase
import com.ikpydev.domain.usecase.group.SendGroupsMessagesUseCase
import com.ikpydev.domain.usecase.group.SendGroupsVoiceMessagesUseCase
import com.ikpydev.domain.usecase.settings.GetInitialScreenUseCase
import com.ikpydev.domain.usecase.settings.OnboardedUseCase
import com.ikpydev.presentation.screens.chat.ChatViewModel
import com.ikpydev.presentation.screens.code.CodeViewModel
import com.ikpydev.presentation.screens.group.GroupViewModel
import com.ikpydev.presentation.screens.home.HomeViewModel
import com.ikpydev.presentation.screens.home.chats.HomeChatsViewModel
import com.ikpydev.presentation.screens.home.groups.HomeGroupsViewModel
import com.ikpydev.presentation.screens.main.MainViewModel
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel
import com.ikpydev.presentation.screens.phone.PhoneViewModel
import com.ikpydev.presentation.utils.DialogHelper
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val cicerone = Cicerone.create()

val config = RealmConfiguration.create(schema = setOf(SettingsRealm::class))

val appModule = module {
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
    single { Realm.open(config) }
    single { ActivityHolder() }
    single { Volley.newRequestQueue(androidContext()) }
    single { DialogHelper(get()) }
}

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<GroupsChatRepository> { GroupsChatRepositoryImpl(get(), get(),get(),get()) }
}


val useCaseModule = module {
    single { SendSmsCodeUseCase(get()) }

    single { OnboardedUseCase(get()) }
    single { GetInitialScreenUseCase(get(), get()) }
    single { VerifyCodeUseCase(get()) }
    single { GetChatsUseCase(get()) }
    single { SendMessageUseCase(get()) }
    single { GetMessageUseCase(get()) }
    single { SendImageUseCase(get()) }
    single { SendVoiceUseCase(get()) }
    single { GetGroupsChatsUseCase(get()) }
    single { GetGroupsMessagesUseCase(get()) }
    single { CreateGroupsUseCase(get()) }
    single { SendGroupsMessagesUseCase(get()) }
    single { SendGroupsImageMessagesUseCase(get()) }
    single { SendGroupsVoiceMessagesUseCase(get()) }
}

val localModel = module {
    single<UserStore> { UserStoreImpl() }
    single<SettingsStore> { SettingsStoreImpl(get()) }
}

val remoteModule = module {
    single<AuthFirebase> { AuthFirebaseImpl(get()) }
    single<UsersFireStore> { UsersFireStoreImpl(get()) }
    single<MessagesFireBase> { MessageFirebaseImpl() }
    single<ImageStorage> { ImageStorageImpl() }
    single<VoiceStorage> { VoiceStorageImpl() }
    single<PushVolley> { PushVolleyImpl(get()) }
    single<GroupsFireStore> { GroupsFireStoreImpl(get()) }
    single<MessagesFireBaseGroup> { MessageFirebaseGroupImpl() }


}

val viewModel = module {
    viewModel { PhoneViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { OnboardedViewModel(get(), get()) }
    viewModel { CodeViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get(),get()) }
    viewModel { ChatViewModel(get(), get(), get(), get()) }
    viewModel { HomeGroupsViewModel(get(), get()) }
    viewModel { HomeChatsViewModel(get(), get()) }
    viewModel { GroupViewModel(get(),get(),get(),get()) }
}

