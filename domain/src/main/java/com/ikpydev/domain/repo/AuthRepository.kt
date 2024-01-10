package com.ikpydev.domain.repo

interface AuthRepository {
    fun sendSmsCode(phone: String)
}