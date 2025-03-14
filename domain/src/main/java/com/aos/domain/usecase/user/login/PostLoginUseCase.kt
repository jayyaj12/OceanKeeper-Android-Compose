package com.aos.domain.usecase.user.login

import com.aos.domain.model.user.login.LoginModel
import com.aos.domain.repository.UserRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        deviceToken: String,
        provider: String,
        providerId: String
    ): Result<LoginModel> {
        return userRepository.postLogin(deviceToken, provider, providerId)
    }

}