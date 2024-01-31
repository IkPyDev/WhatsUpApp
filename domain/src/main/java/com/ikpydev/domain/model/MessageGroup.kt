package com.ikpydev.domain.model

import android.net.Uri
import java.util.Date

data class MessageGroup(
    var id: String,
    var message: String? = null,
    var time: Date,
    var type: TypeGroup,
    val image:Uri? = null,
    val voice:Uri? = null
)
enum class TypeGroup{
    text_in,text_out,image_upload,image_in,image_out,voice_upload,voice_in,voice_out
}