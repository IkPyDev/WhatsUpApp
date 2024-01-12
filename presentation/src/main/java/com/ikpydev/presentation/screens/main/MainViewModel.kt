package com.ikpydev.presentation.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.usecase.settings.GetOnboardedUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.OnboardedScreen
import com.ikpydev.presentation.navigation.Screens.PhoneScreen
import com.ikpydev.presentation.screens.main.MainViewModel.Effect
import com.ikpydev.presentation.screens.main.MainViewModel.Input
import com.ikpydev.presentation.screens.main.MainViewModel.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val router: Router,
    private val getOnboardedUseCase: GetOnboardedUseCase
) : BaseViewModel<State, Input, Effect>() {

    class State

    class Input

    class Effect

    init {
        getOnboarded()
    }

    override fun processInput(input: Input) {

    }

    override fun getDefaultState() = State()

    private fun getOnboarded() {

        getOnboardedUseCase().subscribe { onboarded ->
            Log.d("TAG", "getOnboarded:$onboarded ")
            router.newRootScreen(if (onboarded) PhoneScreen() else OnboardedScreen())
        }

    }
}



