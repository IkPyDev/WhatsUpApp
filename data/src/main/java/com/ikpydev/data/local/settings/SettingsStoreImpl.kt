package com.ikpydev.data.local.settings

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class SettingsStoreImpl(private val realm: Realm) : SettingsStore {

    private fun getSettings(): Single<SettingsRealm> = Single.fromCallable {

        realm.query<SettingsRealm>().find().firstOrNull() ?: kotlin.run {
            realm.writeBlocking {
                copyToRealm(SettingsRealm())
            }
        }

    }

    override fun onboarded() = Completable.fromCallable {
            realm.writeBlocking {
                query<SettingsRealm>().find().firstOrNull()?.onboarded = true

            }

        }



    override fun getOnboarded() = getSettings().map { it.onboarded }
}