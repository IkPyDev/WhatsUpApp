package com.ikpydev.presentation.screens.home

import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Group
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.usecase.chat.GetChatsUseCase
import com.ikpydev.domain.usecase.group.GetGroupsChatsUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.ChatScreen
import com.ikpydev.presentation.screens.home.HomeViewModel.Effect
import com.ikpydev.presentation.screens.home.HomeViewModel.Input
import com.ikpydev.presentation.screens.home.HomeViewModel.State


class HomeViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val router: Router,
    private val getGroupsChatsUseCase: GetGroupsChatsUseCase

) : BaseViewModel<State, Input, Effect>() {


    data class State(
        val chats: List<Chat> = emptyList(),
        val loading: Boolean = false,
        val error: Boolean = false,
        val errorGroups: Boolean = false,
        val groupChats:List<GroupChat> = emptyList()
    )

    sealed class Input {
        object GetChats : Input()
        data class OpenChat(val chat: Chat) : Input()
    }

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when (input) {
            Input.GetChats -> getChats()
            is Input.OpenChat -> openChat(input.chat)
            else -> {}
        }
    }

    private fun openChat(chat: Chat) {

        router.navigateTo(ChatScreen(chat))
    }
    private fun openChat(groupChat: GroupChat) {


    }



    private fun getChats() = getChatsUseCase()
        .doOnSubscribe {
            updateState { it.copy(loading = true, error = false) }
        }
        .doOnError {
            updateState { it.copy(loading = false, error = true) }
        }
        .doAfterSuccess { chats ->
            updateState { it.copy(chats = chats) }
        }
        .doFinally {
            updateState { it.copy(loading = false) }
        }.subscribe()
    private fun getGroupsChats() = getGroupsChatsUseCase()
        .doOnSubscribe {
            updateState { it.copy(loading = true, errorGroups = false) }
        }
        .doOnError {
            updateState { it.copy(loading = false, errorGroups = true) }
        }
        .doAfterSuccess { groups ->
            updateState { it.copy(groupChats =  groups) }
        }
        .doFinally {
            updateState { it.copy(loading = false) }
        }.subscribe()

}