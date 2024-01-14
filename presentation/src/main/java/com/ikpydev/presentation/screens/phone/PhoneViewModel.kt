package com.ikpydev.presentation.screens.phone

import com.github.terrakok.cicerone.Router
import com.ikpydev.domain.usecase.auth.SendSmsCodeUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.CodeScreen
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Effect
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Input
import com.ikpydev.presentation.screens.phone.PhoneViewModel.State

class PhoneViewModel constructor(
    private val sendSmsCodeUseCase: SendSmsCodeUseCase,
    private val router: Router
) : BaseViewModel<State, Input, Effect>() {


    data class State(
        val loading: Boolean = false
    )

    sealed class Input {
        data class SendCode(val phone: String) : Input()
    }

    sealed class Effect {
        object Error : Effect()
    }

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {

        when (input) {
            is Input.SendCode -> sendCode(input.phone)
        }


    }

    private fun sendCode(phone: String) = sendSmsCodeUseCase(phone)
        .doOnSubscribe {
            updateState { it.copy(loading = true) }
        }.doOnComplete {
            router.navigateTo(CodeScreen(phone))
        }.doFinally {
            updateState { it.copy(loading = false) }
        }.subscribe({},{
            emitEffects(Effect.Error)
        })

}