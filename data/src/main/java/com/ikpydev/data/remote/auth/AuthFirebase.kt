package com.ikpydev.data.remote.auth

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface AuthFirebase {
    fun sendSmsCode(phone: String): Completable

    fun verify(code: String): Single<FirebaseUser>
    val isLoggedIn : Boolean
}