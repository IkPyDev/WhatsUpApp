package com.ikpydev.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ikpydev.domain.model.Chat
import com.ikpydev.presentation.screens.chat.ChatFragment
import com.ikpydev.presentation.screens.code.CodeFragment
import com.ikpydev.presentation.screens.home.HomeFragment
import com.ikpydev.presentation.screens.home.chats.ChatsFragment
import com.ikpydev.presentation.screens.home.groups.GroupsFragment
import com.ikpydev.presentation.screens.onboarded.OnboardedFragment
import com.ikpydev.presentation.screens.phone.PhoneFragment

object Screens {

    fun PhoneScreen() = FragmentScreen { PhoneFragment() }
    fun OnboardedScreen() = FragmentScreen { OnboardedFragment() }
    fun CodeScreen(phone: String) = FragmentScreen { CodeFragment(phone) }
    fun HomeScreen() = FragmentScreen { HomeFragment() }
    fun ChatScreen(chat: Chat) = FragmentScreen { ChatFragment(chat) }

    fun ChatsScreen() = FragmentScreen { ChatsFragment() }
    fun GroupsScreen() = FragmentScreen { GroupsFragment() }
}