package com.ikpydev.domain.model

data class Group(
    var _id: String? = null,
    val create: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val members: List<String>? = null
)
