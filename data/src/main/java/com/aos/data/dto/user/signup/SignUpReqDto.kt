package com.aos.data.dto.user.signup

import kotlinx.serialization.Serializable

@Serializable
data class SignUpReqDto (
    val deviceToken: String,
    val email: String,
    val nickname: String,
    val profile: String,
    val provider: String,
    val providerId: String
)