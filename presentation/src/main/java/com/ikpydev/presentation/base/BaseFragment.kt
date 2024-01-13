package com.ikpydev.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.core.Observable

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
     val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    fun <T : Any, R> Observable<T>.observe(observer: (R) -> Unit, mapper: (T) -> R) {
        map(mapper).distinctUntilChanged().doOnNext(observer).subscribe()
    }

    fun snackbar(massage: String) {
        Snackbar.make(binding.root, massage, Snackbar.LENGTH_SHORT).show()
    }

    fun snackbar(massageId: Int) = snackbar(getString(massageId))


}