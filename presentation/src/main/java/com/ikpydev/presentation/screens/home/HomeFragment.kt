package com.ikpydev.presentation.screens.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.ikpydev.domain.model.Chat
import com.ikpydev.domain.model.Group
import com.ikpydev.domain.model.GroupChat
import com.ikpydev.domain.model.UserResult
import com.ikpydev.presentation.R
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentHomeBinding
import com.ikpydev.presentation.screens.home.HomeViewModel.*
import com.ikpydev.presentation.utils.DialogHelper
import org.koin.android.ext.android.inject

import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
//    private lateinit var pagerAdapter: HomePagerAdapter
    private lateinit var tabAdapter:TabLayoutAdapter
    private lateinit var userData : List<Chat>
    private lateinit var dialogHelper:DialogHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogHelper = DialogHelper(requireContext())


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabAdapter = TabLayoutAdapter(childFragmentManager,lifecycle)

        initUi()
        observeViewModel()


    }

    private fun observeViewModel() {


        viewModel.state.observe(::renderError) { it.error }
        viewModel.state.observe(::renderChat) { it.chats }
        viewModel.state.observe(::renderLoading) { it.loading }
//        viewModel.state.observe(::renderGroupChats) { it.groupChats }
        viewModel.state.observe(::renderErrorGroups) { it.errorGroups }
    }

    private fun renderLoading(loading: Boolean) {

    }
    private fun renderChat(chat: List<Chat>) {

        userData = chat
    }

    private fun renderError(error: Boolean) {

    }



    private fun initUi() = with(binding) {

        tabMode.addTab(tabMode.newTab().setText(R.string.chats).setIcon(R.drawable.ic_person))
        tabMode.addTab(tabMode.newTab().setText(R.string.groups).setIcon(R.drawable.ic_groups_24))

        viewpager.adapter = tabAdapter
        tabMode.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    viewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabMode.selectTab(tabMode.getTabAt(position))
            }
        })

        add.setOnClickListener {

            val position = viewpager.currentItem
            if (position == 1) {

                dialogHelper.showUserDialog2(userData,::newGroups)

            }else {
                Toast.makeText(requireContext(), "One Create Group", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun newGroups(userResult: UserResult) {
        viewModel.processInput(Input.UserRe(userResult))


    }

    private fun onClickGroup(groupChat: GroupChat) {

    }






    private fun renderErrorGroups(error: Boolean) {

    }




}

//                val userGroup = showUserDialog2(users, requireContext())
//                Log.d(
//                    "UserGroup",
//                    "Group ID: ${userGroup.groupId}, User IDs: ${userGroup.userIds.joinToString()}"
//

//        pagerAdapter = HomePagerAdapter(listOf(), listOf(), ::onClickChat, ::onClickGroup)
//        viewpager.adapter = pagerAdapter

//        val tabTitles = listOf("Chats", "Groups") // Tablarni nomlarini ro'yxat
//        TabLayoutMediator(tabMode, viewpager) { tab, position ->
//            tab.text = tabTitles[position] // Tab nomini belgilash
//        }.attach()