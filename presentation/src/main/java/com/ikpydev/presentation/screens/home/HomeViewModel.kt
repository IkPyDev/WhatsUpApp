package com.ikpydev.presentation.screens.home

import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.usecase.chat.GetChatsUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.home.HomeViewModel.Effect
import com.ikpydev.presentation.screens.home.HomeViewModel.Input
import com.ikpydev.presentation.screens.home.HomeViewModel.State


class HomeViewModel(
    private val getChatsUseCase: GetChatsUseCase
) : BaseViewModel<State, Input, Effect>() {
    init {
        getChats()
    }

    data class State(
        val chats: List<Chat> = emptyList(),
        val loading: Boolean = false,
        val error: Boolean = false
    )

    sealed class Input {
        object GetChats : Input()
    }

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when (input) {
            Input.GetChats -> TODO()
        }
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
}