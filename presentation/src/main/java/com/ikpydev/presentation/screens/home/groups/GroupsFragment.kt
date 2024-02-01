package com.ikpydev.presentation.screens.home.groups

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.ItemGroupsLayoutBinding
import com.ikpydev.presentation.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class GroupsFragment :
    BaseFragment<ItemGroupsLayoutBinding>(ItemGroupsLayoutBinding::inflate) {

    private val viewModel: HomeGroupsViewModel by viewModel()

    private var adapter = GroupsAdapter(listOf(), ::onClickGroup)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initUi()

        viewModel.state.observe(::renderGroupChats) { it.groupChats }

    }

    private fun initUi() = with(binding) {

        groups.adapter = adapter

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderGroupChats(groupChats: List<GroupChat>) {

        adapter.renderGroupChats(groupChats)

    }



    private fun onClickGroup(groups: GroupChat) {
        viewModel.processInput(HomeGroupsViewModel.Input.OpenGroup(groups))

    }

}

