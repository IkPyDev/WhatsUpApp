package com.ikpydev.presentation.screens.code

import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.usecase.auth.VerifyCodeUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.HomeScreen
import com.ikpydev.presentation.screens.code.CodeViewModel.Effect
import com.ikpydev.presentation.screens.code.CodeViewModel.Input
import com.ikpydev.presentation.screens.code.CodeViewModel.State

class CodeViewModel(
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val router: Router
) : BaseViewModel<State, Input, Effect>() {

    data class State(
        val loading: Boolean = false
    )

    sealed class Input {
        data class Verify(val code: String) : Input()
    }

    sealed class Effect {
        object Error : Effect()
    }

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when (input) {
            is Input.Verify -> verify(input.code)
        }
    }

    private fun verify(code: String) = verifyCodeUseCase(code)
        .doOnSubscribe {
            updateState { it.copy(loading = true) }
        }
        .doFinally {
            updateState { it.copy(loading = false) }
        }
        .subscribe(
            {

                router.navigateTo(HomeScreen())
            },
            { _ ->
                emitEffects(Effect.Error)

            }
        )
}