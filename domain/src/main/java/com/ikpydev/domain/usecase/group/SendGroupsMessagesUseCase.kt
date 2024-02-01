package com.ikpydev.domain.usecase.group

import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.repo.GroupsChatRepository

class SendGroupsMessagesUseCase(
    private val groupsChatRepository: GroupsChatRepository
) {
    operator fun invoke(groupChat:GroupChat,messages:String) = groupsChatRepository.sendMessage(groupChat,messages)
}