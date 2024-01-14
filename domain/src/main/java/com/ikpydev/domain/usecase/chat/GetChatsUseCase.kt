package com.ikpydev.domain.usecase.chat

import com.ikpydev.domain.repo.ChatRepository

class GetChatsUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() = chatRepository.getChats()
}