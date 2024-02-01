package com.ikpydev.domain.usecase.group

import com.ikpydev.domain.model.UserResult
import com.ikpydev.domain.repo.GroupsChatRepository

class CreateGroupsUseCase(
    private val groupsChatRepository: GroupsChatRepository
) {
    operator fun invoke(userResult: UserResult) = groupsChatRepository.createGroup(userResult)
}