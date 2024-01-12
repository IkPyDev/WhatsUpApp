package com.ikpydev.domain.usecase.settings

import com.ikpydev.domain.repo.SettingsRepository

class OnboardedUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.onboarded()
}