package com.ikpydev.presentation.screens.chat

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Message
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentChatBinding
import com.ikpydev.presentation.screens.chat.ChatViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.InputStream

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

        Glide.with(root).load(chat.user.avatar).into(avatar)
        name.text = chat.user.name


        gallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        send.setOnClickListener {
            if (message.text.isBlank().not() && message.text.isEmpty().not()) {
                viewModel.processInput(Input.SendMessage(message.text.toString()))
                message.text = null
            }
        }
        messages.adapter = adapter

    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult
        val stream: InputStream = requireActivity().contentResolver.openInputStream(it)
            ?: return@registerForActivityResult
        viewModel.processInput(Input.SendImage(it, stream))
    }
}