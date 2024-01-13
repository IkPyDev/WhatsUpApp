package com.ikpydev.domain.usecase.auth

import com.ikpydev.domain.repo.AuthRepository

class VerifyCodeUseCase(
    private val authRepository: AuthRepository
) {

    operator fun invoke(code: String) = authRepository.verify(code)


}