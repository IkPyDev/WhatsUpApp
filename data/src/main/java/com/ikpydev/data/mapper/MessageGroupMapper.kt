package com.ikpydev.data.mapper

import android.net.Uri
import com.ikpydev.data.remote.groups.model.GroupDocument
import com.ikpydev.data.remote.messageGroup.MessageGroupDocument
import com.ikpydev.data.remote.messages.MessageDocument
import com.ikpydev.domain.model.Group
import com.ikpydev.domain.model.Message
import com.ikpydev.domain.model.MessageGroup
import com.ikpydev.domain.model.Type
import com.ikpydev.domain.model.TypeGroup

fun MessageGroupDocument.toMessageGroup(userId: String): MessageGroup? {
    return MessageGroup(
        id = id ?: return null,
        message = message,
        time = time ?: return null,
        type = when (from) {
            userId -> when {
                image != null -> TypeGroup.image_out
                voice != null -> TypeGroup.voice_out
                else -> TypeGroup.text_out
            }

            else -> when {
                image != null -> TypeGroup.image_in
                voice != null -> TypeGroup.voice_in

                else -> TypeGroup.text_in

            }
        },
        image = image?.let { Uri.parse(image) },
        voice = voice?.let { Uri.parse(voice) },
        avatar = avatar


    )
}