package com.ikpydev.presentation.screens.onboarded

import android.annotation.SuppressLint
import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.usecase.settings.OnboardedUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.PhoneScreen
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel.Effects
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel.Input
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel.State

class OnboardedViewModel(
    private val onboardedUseCase: OnboardedUseCase,
    private val router: Router
) : BaseViewModel<State, Input, Effects>() {


    class State

    sealed class Input {

        object Onboard : Input()
    }

    class Effects

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when (input) {
            Input.Onboard -> onboarded()
        }
    }

    @SuppressLint("CheckResult")
    private fun onboarded() {
        onboardedUseCase().subscribe {
            router.replaceScreen(PhoneScreen())
        }
    }
}