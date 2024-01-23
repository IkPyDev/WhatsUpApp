package com.ikpydev.domain.usecase.chat

import com.ikpydev.domain.model.User
import com.ikpydev.domain.repo.ChatRepository
import java.io.InputStream

class SendVoiceUseCase(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(to: User, voice: InputStream) = chatRepository.sendMessageVoice(to, voice)
}