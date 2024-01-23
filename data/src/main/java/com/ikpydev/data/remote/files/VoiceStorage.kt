package com.ikpydev.data.remote.files

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.io.InputStream

interface VoiceStorage {
    fun upload(voice : InputStream): Single<Uri>
}