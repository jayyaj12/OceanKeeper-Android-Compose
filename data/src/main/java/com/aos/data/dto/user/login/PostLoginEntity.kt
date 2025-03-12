package com.aos.data.dto.user.login

import com.aos.domain.model.LoginModel
import kotlinx.serialization.Serializable

@Serializable
data class PostLoginEntity(
    val token: Token,
    val user: User
)

fun PostLoginEntity.toLoginModel(): LoginModel {
    return LoginModel(
        com.aos.domain.model.Token(
            this.token.accessToken,
            this.token.refreshToken
        ),
        com.aos.domain.model.User(
            this.user.id,
            this.user.nickname,
            this.user.profile
        )
    )
}