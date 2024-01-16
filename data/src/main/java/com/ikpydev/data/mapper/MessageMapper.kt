package com.ikpydev.data.mapper

import com.ikpydev.data.remote.messages.MessageDocument
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.Type

fun MessageDocument.toMessage(userId: String): Message? {
    return Message(
        id = id ?: return null,
        message = message ?: return null,
        time = time ?: return null,
        type = if (from == userId) Type.text_out else Type.text_in


    )
}