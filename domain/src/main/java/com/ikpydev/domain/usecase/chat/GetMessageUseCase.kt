package com.ikpydev.domain.usecase.chat

import com.ikpydev.domain.repo.ChatRepository

class GetMessageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(with: String) = chatRepository.getMessage(with)
}