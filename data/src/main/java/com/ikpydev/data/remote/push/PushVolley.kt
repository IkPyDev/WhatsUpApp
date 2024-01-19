package com.ikpydev.data.remote.push

import io.reactivex.rxjava3.core.Completable

interface PushVolley {
    fun push(token: String?, title: String, body: String): Completable
}