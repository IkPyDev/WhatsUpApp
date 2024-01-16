package com.ikpydev.presentation.screens.chat

import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.User
import com.ikpydev.domain.usecase.chat.GetMessageUseCase
import com.ikpydev.domain.usecase.chat.SendMessageUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.chat.ChatViewModel.Effect
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import com.ikpydev.presentation.screens.chat.ChatViewModel.State


class ChatViewModel(
    private val getMessageUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : BaseViewModel<State, Input, Effect>() {



    data class State(
        val messages: List<Message> = emptyList(),
        val loading: Boolean = false,
        val chat: Chat? = null
    )

    sealed class Input {
        object GetMessage : Input()
        data class SendMessage(val message: String) : Input()
        data class SetChat(val chat: Chat) : Input()
    }

    sealed class Effect {
        object ErrorSending : Effect()
        object ErrorGetting : Effect()
    }

    override fun getDefaultState() = State()
    private fun getMessage() = getMessageUseCase(current.chat!!.user.id)
        .doOnSubscribe {
            updateState { it.copy(loading = true) }
        }
        .doOnEach {
            updateState { it.copy(loading = false) }
        }
        .doOnNext { message ->
            updateState { it.copy(messages = message) }
        }.subscribe({}, {
            emitEffects(Effect.ErrorGetting)
        })

    private fun senMessage(message: String) = sendMessageUseCase(current.chat!!.user.id, message)
        .subscribe({}, {
            emitEffects(Effect.ErrorSending)
        })

    override fun processInput(input: Input) {
        when (input) {
            Input.GetMessage -> getMessage()
            is Input.SendMessage -> senMessage(input.message)
            is Input.SetChat -> setUser(input.chat)
        }
    }

    private fun setUser(chat: Chat) {
        updateState { it.copy(chat = chat) }
        getMessage()
    }


}