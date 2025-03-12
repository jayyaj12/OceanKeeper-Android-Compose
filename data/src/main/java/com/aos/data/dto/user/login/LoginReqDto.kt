package com.aos.data.dto.user.login

import com.aos.domain.model.LoginModel
import com.aos.domain.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class LoginReqDto(
    val deviceToken: String,
    val provider: String,
    val providerId: String
)