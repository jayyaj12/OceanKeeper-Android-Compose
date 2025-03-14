package com.aos.data.dto.user.signup

import com.aos.domain.model.user.signup.AuthSignUpModel
import kotlinx.serialization.Serializable

@Serializable
data class PostAuthSingUpEntity(
    val response: PostAuthSignUpResponse
)

@Serializable
data class PostAuthSignUpResponse(
    val id: String,
    val nickname: String,
    val profile: String
)

fun PostAuthSingUpEntity.toAuthSignUpModel(): AuthSignUpModel {
    return AuthSignUpModel(
        nickname = this.response.nickname,
        profile = this.response.profile
    )
}