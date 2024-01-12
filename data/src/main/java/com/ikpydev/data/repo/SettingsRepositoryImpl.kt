package com.ikpydev.data.repo

import android.util.Log
import com.ikpydev.data.local.settings.SettingsStore
import com.ikpydev.domain.repo.SettingsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingsRepositoryImpl(private val settingsStore: SettingsStore) : SettingsRepository {
    override fun onboarded(): Completable = settingsStore
        .onboarded()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorComplete{
            Log.e(
                "SettingsRepositoryImpl", "Error in onboarded: $it"
            )
            false
        }



    override fun getOnboarded(): Single<Boolean> = settingsStore
        .getOnboarded()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}