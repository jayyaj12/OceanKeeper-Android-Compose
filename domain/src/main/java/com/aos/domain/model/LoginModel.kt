package com.aos.domain.model

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
