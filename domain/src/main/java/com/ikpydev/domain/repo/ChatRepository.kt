package com.ikpydev.domain.repo

import com.ikpydev.domain.model.Chat
import io.reactivex.rxjava3.core.Single

interface ChatRepository {
    fun getChats(): Single<List<Chat>>
}