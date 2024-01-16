package com.ikpydev.domain.model

import java.util.Date

data class Message(
    var id: String,
    var message: String,
    var time: Date,
    var type: Type
)
enum class Type{
    text_in,text_out
}