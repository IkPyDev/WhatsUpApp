package com.ikpydev.presentation.screens.code

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.ikpydev.presentation.R
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmentOtpBinding
import com.ikpydev.presentation.screens.code.CodeViewModel.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CodeFragment(
    private val phone: String
) : BaseFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {

    private val viewModel: CodeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        viewModel.effect.subscribe(::handEffects)

    }

    private fun handEffects(effect: Effect) = with(binding) {

        when(effect){
            Effect.Error -> {
                snackbar(R.string.code_error)
                error.isVisible= true
                otpView.showError()

            }
        }
    }

    private fun initUi() = with(binding) {

        verify.setOnClickListener {
            val otp = otpView.otp?.takeIf { it.length == 6 } ?: return@setOnClickListener
            viewModel.processInput(Input.Verify(otp))

        }

        title.text = getString(R.string.send_code,phone)

    }

}