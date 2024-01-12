package com.ikpydev.presentation.screens.onboarded

import android.os.Bundle
import android.view.View
import com.ikpydev.presentation.base.BaseFragment
import com.ikpydev.presentation.databinding.FragmnetOnboardedBinding
import com.ikpydev.presentation.screens.onboarded.OnboardedViewModel.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OnboardedFragment :
    BaseFragment<FragmnetOnboardedBinding>(FragmnetOnboardedBinding::inflate) {

    private val viewModel:OnboardedViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI()= with(binding) {

        next.setOnClickListener {
            viewModel.processInput(Input.Onboard)
        }

    }
}