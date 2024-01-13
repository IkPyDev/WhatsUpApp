package com.ikpydev.data.remote.auth

import io.reactivex.rxjava3.core.Completable

interface AuthFirebase {
    fun sendSmsCode(phone: String): Completable

    fun verify(code: String): Completable
    val isLoggedIn : Boolean
}