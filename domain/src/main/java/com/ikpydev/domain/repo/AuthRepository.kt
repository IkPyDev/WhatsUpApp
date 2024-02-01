package com.ikpydev.domain.repo

import io.reactivex.rxjava3.core.Completable

interface AuthRepository {
    fun sendSmsCode(phone: String): Completable
    fun verify(code: String): Completable
    val isLoggedIn: Boolean
    val userId :String
}