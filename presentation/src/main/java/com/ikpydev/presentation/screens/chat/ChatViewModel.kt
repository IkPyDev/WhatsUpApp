package com.ikpydev.presentation.screens.chat

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.Type
import com.ikpydev.domain.usecase.chat.GetMessageUseCase
import com.ikpydev.domain.usecase.chat.SendImageUseCase
import com.ikpydev.domain.usecase.chat.SendMessageUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.chat.ChatViewModel.Effect
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import com.ikpydev.presentation.screens.chat.ChatViewModel.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.util.Date


class ChatViewModel(
    private val getMessageUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val sendImageUseCase: SendImageUseCase
) : BaseViewModel<State, Input, Effect>() {



    data class State(
        val messages: List<Message> = emptyList(),
        val loading: Boolean = false,
        val chat: Chat? = null
    )

    sealed class Input {
        object GetMessage : Input()
        data class SendMessage(val message: String) : Input()
        data class SendImage(val image: Uri, val stream: InputStream) : Input()
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

    private fun senMessage(message: String) = sendMessageUseCase(current.chat!!.user, message)
        .subscribe({}, {
            emitEffects(Effect.ErrorSending)
        })

    override fun processInput(input: Input) {
        when (input) {
            Input.GetMessage -> getMessage()
            is Input.SendMessage -> senMessage(input.message)
            is Input.SetChat -> setUser(input.chat)
            is Input.SendImage -> sendImage(input.image, input.stream)
            else -> {}
        }
    }

    private fun sendImage(image: Uri, stream: InputStream) {

        val message =
            Message(id = image.toString(), time = Date(), type = Type.image_upload, image = image)
        val messages = current.messages.toMutableList()
        messages.add(message)
        updateState { it.copy(messages = messages) }
        viewModelScope.launch(Dispatchers.IO) {
            sendImageUseCase(current.chat!!.user, stream)
                .subscribe({}, {})
        }
    }

    private fun setUser(chat: Chat) {
        updateState { it.copy(chat = chat) }
        getMessage()
    }


}