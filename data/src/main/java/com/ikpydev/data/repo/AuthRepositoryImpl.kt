package com.ikpydev.data.repo

import com.ikpydev.data.remote.auth.AuthFirebase
import com.ikpydev.domain.repo.AuthRepository

class AuthRepositoryImpl constructor(private val authFirebase: AuthFirebase) : AuthRepository {
    override fun sendSmsCode(phone: String) {
        authFirebase.sendSmsCode(phone)
    }
}