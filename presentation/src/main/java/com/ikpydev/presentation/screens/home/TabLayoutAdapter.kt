package com.ikpydev.presentation.screens.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ikpydev.presentation.screens.home.chats.ChatsFragment
import com.ikpydev.presentation.screens.home.groups.GroupsFragment

class TabLayoutAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ChatsFragment() else GroupsFragment()

    }

}