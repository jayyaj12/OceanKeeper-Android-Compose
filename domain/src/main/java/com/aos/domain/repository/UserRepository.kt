package com.aos.domain.repository

import com.aos.domain.model.LoginModel

interface UserRepository {

    suspend fun postLogin(deviceToken: String, provider: String, providerId: String): Result<LoginModel>

}