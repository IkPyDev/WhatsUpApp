package com.ikpydev.data.repo

import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.data.remote.users.UsersFireStore
import com.ikpydev.domain.repo.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthRepositoryImpl constructor(
    private val authFirebase: AuthFirebase,
    private val usersFireStore: UsersFireStore
) : AuthRepository {
    override fun sendSmsCode(phone: String) = authFirebase.sendSmsCode(phone)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    override fun verify(code: String): Completable = authFirebase.verify(code)
        .flatMapCompletable {
            usersFireStore.setUser(it)
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override val isLoggedIn: Boolean
        get() = authFirebase.isLoggedIn


}