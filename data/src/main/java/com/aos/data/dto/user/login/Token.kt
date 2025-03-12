package com.aos.data.dto.user.login

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String,
    val accessTokenExpiresIn: Long,
    val grantType: String,
    val refreshToken: String
)