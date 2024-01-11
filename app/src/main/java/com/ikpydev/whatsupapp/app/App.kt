package com.ikpydev.whatsupapp.app

import android.app.Application
import com.ikpydev.whatsupapp.di.appModule
import com.ikpydev.whatsupapp.di.localModel
import com.ikpydev.whatsupapp.di.remoteModule
import com.ikpydev.whatsupapp.di.repositoryModule
import com.ikpydev.whatsupapp.di.useCaseModule
import com.ikpydev.whatsupapp.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(repositoryModule, useCaseModule, localModel, remoteModule, viewModel, appModule)
        }
    }
}