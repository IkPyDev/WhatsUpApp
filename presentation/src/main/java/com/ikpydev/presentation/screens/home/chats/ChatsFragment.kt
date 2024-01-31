package com.ikpydev.presentation.screens.home.chats

import android.os.Bundle
import android.util.Log
import android.view.View
import com.ikpydev.domain.model.Chat
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.ItemChatsLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChatsFragment :
    BaseFragment<ItemChatsLayoutBinding>(ItemChatsLayoutBinding::inflate) {

    private val viewModel: HomeChatsViewModel by viewModel()
    private var adapter = ChatsAdapter(listOf(), ::onClickChat)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initUi()

    }

    private fun observeViewModel() {
        viewModel.state.observe(::renderChats) { it.chats }


        viewModel.state.observe(::renderError) { it.error }
        viewModel.state.observe(::renderLoading) { it.loading }

    }

    private fun renderLoading(loading: Boolean) {

    }

    private fun renderError(error: Boolean) {

    }

    private fun renderChats(chat: List<Chat>) {

        adapter.updateChats(chat)
    }

    private fun initUi() = with(binding) {

        chats.adapter = adapter

    }

    private fun onClickChat(chat: Chat) {
        viewModel.processInput(HomeChatsViewModel.Input.OpenChat(chat))

    }


}