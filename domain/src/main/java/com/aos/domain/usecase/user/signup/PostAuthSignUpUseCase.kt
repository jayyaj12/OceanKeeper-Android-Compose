package com.aos.domain.usecase.user.signup

import com.aos.domain.model.user.signup.AuthSignUpModel
import com.aos.domain.repository.user.UserRepository
import javax.inject.Inject

class PostAuthSignUpUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        deviceToken: String,
        email: String,
        nickname: String,
        profile: String,
        provider: String,
        providerId: String
    ): Result<AuthSignUpModel> {
        return userRepository.postAuthSignup(deviceToken, email, nickname, profile, provider, providerId)
    }
}