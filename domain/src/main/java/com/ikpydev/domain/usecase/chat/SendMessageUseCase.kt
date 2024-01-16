package com.ikpydev.domain.usecase.chat

import com.ikpydev.domain.repo.ChatRepository

class SendMessageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(to: String, message: String) = chatRepository.sendMessage(to, message)
}