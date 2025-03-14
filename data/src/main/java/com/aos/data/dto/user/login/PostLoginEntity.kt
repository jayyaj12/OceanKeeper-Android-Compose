package com.aos.data.dto.user.login

import com.aos.domain.model.user.login.LoginModel
import kotlinx.serialization.Serializable

@Serializable
data class PostLoginEntity(
    val response: PostLoginResponse
)

@Serializable
data class PostLoginResponse(
    val token: Token,
    val user: User
)

fun PostLoginEntity.toLoginModel(): LoginModel {
    return LoginModel(
        com.aos.domain.model.user.login.Token(
            this.response.token.accessToken,
            this.response.token.refreshToken
        ),
        com.aos.domain.model.user.login.User(
            this.response.user.id,
            this.response.user.nickname,
            this.response.user.profile
        )
    )
}