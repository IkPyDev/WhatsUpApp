package com.ikpydev.presentation.screens.phone

import com.ikpydev.domain.model.User
import com.ikpydev.domain.usecase.auth.SendSmsCodeUseCase
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Effect
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Input
import com.ikpydev.presentation.screens.phone.PhoneViewModel.State

class PhoneViewModel constructor(
    private val sendSmsCodeUseCase: SendSmsCodeUseCase
) : BaseViewModel<State, Input, Effect>() {


    data class State(
         val user: User? = null
    )

    class Input

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
    }
}