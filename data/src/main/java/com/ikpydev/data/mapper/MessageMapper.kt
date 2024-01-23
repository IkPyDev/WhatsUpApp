package com.ikpydev.data.mapper

import android.net.Uri
import com.ikpydev.data.remote.messages.MessageDocument
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.Type

fun MessageDocument.toMessage(userId: String): Message? {
    return Message(
        id = id ?: return null,
        message = message,
        time = time ?: return null,
        type = when (from) {
            userId -> when {
                image != null -> Type.image_out
                voice != null -> Type.voice_out
                else -> Type.text_out
            }

            else -> when {
                image != null -> Type.image_in
                voice != null -> Type.voice_in

                else -> Type.text_in

            }
        },
        image = image?.let { Uri.parse(image) },
        voice = voice?.let { Uri.parse(voice) }


    )
}