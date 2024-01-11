package com.ikpydev.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ikpydev.presentation.screens.phone.PhoneFragment

object Screens {

    fun Phone() = FragmentScreen { PhoneFragment() }
}