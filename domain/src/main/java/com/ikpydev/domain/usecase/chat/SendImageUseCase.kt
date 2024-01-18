package com.ikpydev.domain.usecase.chat

import com.ikpydev.domain.repo.ChatRepository
import java.io.InputStream

class SendImageUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(to: String, image: InputStream) = chatRepository.sendMessage(to, image)
}