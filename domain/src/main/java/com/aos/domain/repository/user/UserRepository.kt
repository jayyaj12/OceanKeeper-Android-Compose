package com.aos.domain.repository.user

import com.aos.domain.model.user.login.LoginModel
import com.aos.domain.model.user.signup.AuthSignUpModel
import com.aos.domain.model.user.signup.ImageProfileModel
import java.io.File

interface UserRepository {

    suspend fun postLogin(deviceToken: String, provider: String, providerId: String): Result<LoginModel>
    suspend fun postImageProfile(file: File): Result<ImageProfileModel>
    suspend fun postAuthSignup(deviceToken: String, email: String, nickname:String, profile: String, provider: String, providerId: String): Result<AuthSignUpModel>
}