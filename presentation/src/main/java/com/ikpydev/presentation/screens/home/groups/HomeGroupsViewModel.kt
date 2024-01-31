package com.ikpydev.presentation.screens.home.groups

import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.usecase.group.GetGroupsChatsUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.home.groups.HomeGroupsViewModel.Effect
import com.ikpydev.presentation.screens.home.groups.HomeGroupsViewModel.Input
import com.ikpydev.presentation.screens.home.groups.HomeGroupsViewModel.State


class HomeGroupsViewModel(
    private val router: Router,
    private val getGroupsChatsUseCase: GetGroupsChatsUseCase

) : BaseViewModel<State, Input, Effect>() {
    init {
        getGroupsChats()
    }

    data class State(
        val loading: Boolean = false,
        val error: Boolean = false,
        val groupChats:List<GroupChat> = emptyList()
    )

    sealed class Input {
        object GetGroups : Input()
        data class OpenChat(val groupChat: GroupChat) : Input()
    }

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when (input) {
            Input.GetGroups -> getGroupsChats()
            is Input.OpenChat -> openGroups(input.groupChat)
        }
    }

    private fun openGroups(groupChat: GroupChat) {

//        router.navigateTo(GroupsScreen(chat))
    }






    private fun getGroupsChats() = getGroupsChatsUseCase()
        .doOnSubscribe {
            updateState { it.copy(loading = true, error = false) }
        }
        .doOnError {
            updateState { it.copy(loading = false, error = true) }
        }
        .doAfterSuccess { groups ->
            updateState { it.copy(groupChats =  groups) }
        }
        .doFinally {
            updateState { it.copy(loading = false) }
        }.subscribe()

}