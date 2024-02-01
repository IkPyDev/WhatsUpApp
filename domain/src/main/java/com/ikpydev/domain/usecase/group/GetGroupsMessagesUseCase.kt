package com.ikpydev.domain.usecase.group

import com.ikpydev.domain.repo.GroupsChatRepository

class GetGroupsMessagesUseCase(
    private val groupsChatRepository: GroupsChatRepository
) {
    operator fun invoke(groupId:String) = groupsChatRepository.getMessage(groupId)
}