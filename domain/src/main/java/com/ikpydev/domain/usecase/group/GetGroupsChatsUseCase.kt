package com.ikpydev.domain.usecase.group

import com.ikpydev.domain.repo.GroupsChatRepository

class GetGroupsChatsUseCase(
    private val groupsChatRepository: GroupsChatRepository
) {
    operator fun invoke() = groupsChatRepository.getGroupsChats()
}