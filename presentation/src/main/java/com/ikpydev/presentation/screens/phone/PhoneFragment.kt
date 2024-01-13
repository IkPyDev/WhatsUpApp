package com.ikpydev.presentation.screens.phone

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.ikpydev.presentation.R
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentPhoneBinding
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Effect
import com.ikpydev.presentation.screens.phone.PhoneViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneFragment : BaseFragment<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {

    private val viewModel: PhoneViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        viewModel.effect.doOnNext(::handlerEffects)
        viewModel.state.observe(::renderLoading) { it.loading }

    }

    private fun renderLoading(loading: Boolean) = with(binding) {
        progress.isVisible = loading
        sign.text = getText(R.string.sing_in).takeIf { loading.not() }


    }

    private fun initUI() = with(binding) {
        phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        sign.setOnClickListener {
            viewModel.processInput(Input.SendCode(phone.text.toString()))
        }

    }

    private fun handlerEffects(effect: Effect) {

        when (effect) {
            Effect.Error -> snackbar(R.string.phone_error)
        }

    }


}