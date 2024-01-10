package com.ikpydev.presentation.screens.phone

import android.os.Bundle
import android.view.View
import com.ikpydev.domain.model.User
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentPhoneBinding
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Effect

class PhoneFragment : BaseFragment<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {

    private lateinit var viewModel: PhoneViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(::renderUser) { it.user }
        viewModel.effect.doOnNext(::handlerEffects)
    }

    private fun handlerEffects(effect: Effect) {


    }

    private fun renderUser(user: User?) {

    }

}