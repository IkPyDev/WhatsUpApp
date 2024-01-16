package com.ikpydev.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel<State : Any, Input : Any, Effect : Any> : ViewModel() {

    private val stateSubject = BehaviorSubject.createDefault(this.getDefaultState())
    val state: Observable<State>
        get() = stateSubject
            .distinctUntilChanged()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    val current: State get() = stateSubject.blockingFirst()


    abstract fun getDefaultState(): State

    private val effectsSubject = PublishSubject.create<Effect>()

    val effect: Observable<Effect> get() = effectsSubject

    fun updateState(update: (current: State) -> State) {
        val state = update(stateSubject.blockingFirst())
        stateSubject.onNext(state)
    }

    abstract fun processInput(input: Input)
    fun emitEffects(effect: Effect) {
        effectsSubject.onNext(effect)
    }

}