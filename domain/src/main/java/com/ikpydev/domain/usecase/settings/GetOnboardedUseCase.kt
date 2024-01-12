package com.ikpydev.domain.usecase.settings

import com.ikpydev.domain.repo.SettingsRepository

class GetOnboardedUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getOnboarded()
}