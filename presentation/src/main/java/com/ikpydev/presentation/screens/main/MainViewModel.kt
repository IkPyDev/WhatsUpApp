package com.ikpydev.presentation.screens.main

import com.github.terrakok.cicerone.Router
import com.ikpydev.presentation.base.BaseViewModel
import com.ikpydev.presentation.navigation.Screens.Phone
import com.ikpydev.presentation.screens.main.MainViewModel.Effect
import com.ikpydev.presentation.screens.main.MainViewModel.Input
import com.ikpydev.presentation.screens.main.MainViewModel.State


class MainViewModel(private val router: Router) : BaseViewModel<State, Input, Effect>() {

    class State

    class Input

    class Effect

    init {
        router.newRootScreen(Phone())
    }

    override fun processInput(input: Input) {
        TODO("Not yet implemented")
    }

    override fun getDefaultState() = State()
}



