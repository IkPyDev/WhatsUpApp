package com.ikpydev.presentation.screens.home

import android.os.Bundle
import android.view.View
import com.ikpydev.domain.model.Chat
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(::renderChats) { it.chats }
        viewModel.state.observe(::renderError) { it.error }
        viewModel.state.observe(::renderLoading) { it.loading }
    }

    private fun renderLoading(loading: Boolean) {

    }

    private fun renderError(error: Boolean) {

    }

    private fun renderChats(chats: List<Chat>) {
        binding.chats.adapter = ChatAdapter(chats)

    }
}