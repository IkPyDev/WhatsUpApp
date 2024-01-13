package com.ikpydev.domain.usecase.settings

import com.ikpydev.domain.repo.AuthRepository
import com.ikpydev.domain.repo.SettingsRepository
import io.reactivex.rxjava3.core.Single

class GetInitialScreenUseCase(
    private val settingsRepository: SettingsRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Single<Result> = settingsRepository.getOnboarded().map { onboard ->
        when {
            authRepository.isLoggedIn -> Result.Home
            onboard -> Result.Phone
            else -> Result.Onboarded


        }
    }

    sealed class Result {
        object Onboarded : Result()
        object Home : Result()
        object Phone : Result()
    }
}