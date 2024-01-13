package com.ikpydev.presentation.screens.main

import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.usecase.settings.GetInitialScreenUseCase
import com.ikpydev.domain.usecase.settings.GetInitialScreenUseCase.Result
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.HomeScreen
import com.ikpydev.presentation.navigation.Screens.OnboardedScreen
import com.ikpydev.presentation.navigation.Screens.PhoneScreen
import com.ikpydev.presentation.screens.main.MainViewModel.Effect
import com.ikpydev.presentation.screens.main.MainViewModel.Input
import com.ikpydev.presentation.screens.main.MainViewModel.State


class MainViewModel(
    private val router: Router,
    private val getInitialScreenUseCase: GetInitialScreenUseCase
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

        getInitialScreenUseCase().subscribe { result ->
            val screen = when (result) {
                Result.Home -> HomeScreen()
                Result.Onboarded -> OnboardedScreen()
                Result.Phone -> PhoneScreen()
            }

            router.replaceScreen(screen)
        }

    }
}



