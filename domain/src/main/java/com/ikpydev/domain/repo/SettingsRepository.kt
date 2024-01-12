package com.ikpydev.domain.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface SettingsRepository {
    fun onboarded(): Completable

    fun getOnboarded(): Single<Boolean>

}