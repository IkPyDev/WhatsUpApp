package com.ikpydev.presentation.screens.chat

import android.os.Bundle
import android.view.View
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentChatBinding
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment(
    private val chat: Chat
) : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    private val viewModel: ChatViewModel by viewModel()
    private val adapter = ChatAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.processInput(Input.SetChat(chat))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        viewModel.state.observe(::renderMessage){it.messages}
    }

    private fun renderMessage(messages: List<Message>) {
        adapter.submitList(messages)
    }

    private fun initUi() = with(binding) {

        send.setOnClickListener {
            viewModel.processInput(Input.SendMessage(message.text.toString()))
            message.text = null
        }
        messages.adapter = adapter

    }
}