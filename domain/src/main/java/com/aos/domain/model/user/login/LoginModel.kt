package com.aos.domain.model.user.login

data class LoginModel(
    val token: Token,
    val user: User
)

data class Token(
    val accessToken :String,
    val refreshToken: String
)

data class User(
    val userId: String,
    val nickname: String,
    val profile: String
)
