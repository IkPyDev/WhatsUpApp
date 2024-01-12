package com.ikpydev.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ikpydev.presentation.screens.onboarded.OnboardedFragment
import com.ikpydev.presentation.screens.phone.PhoneFragment

object Screens {

    fun PhoneScreen() = FragmentScreen { PhoneFragment() }
    fun OnboardedScreen() = FragmentScreen { OnboardedFragment() }
}