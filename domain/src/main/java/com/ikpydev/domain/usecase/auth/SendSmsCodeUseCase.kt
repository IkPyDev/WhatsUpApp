package com.ikpydev.domain.usecase.auth

import com.ikpydev.domain.repo.AuthRepository

class SendSmsCodeUseCase constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(phone: String) {
        authRepository.sendSmsCode(phone)

    }
}