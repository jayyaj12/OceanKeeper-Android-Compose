package com.aos.data.dto.user.login

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val nickname: String,
    val profile: String
)