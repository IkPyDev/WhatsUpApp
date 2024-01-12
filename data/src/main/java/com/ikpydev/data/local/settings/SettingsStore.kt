package com.ikpydev.data.local.settings

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SettingsStore {

    fun onboarded(): Completable

    fun getOnboarded(): Single<Boolean>
}