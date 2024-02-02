package com.ikpydev.domain.usecase.group

import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.repo.GroupsChatRepository
import java.io.InputStream

class SendGroupsVoiceMessagesUseCase(
    private val groupsChatRepository: GroupsChatRepository
) {
    operator fun invoke(groupChat:GroupChat,voice:InputStream) = groupsChatRepository.sendMessageVoice(groupChat,voice)
}