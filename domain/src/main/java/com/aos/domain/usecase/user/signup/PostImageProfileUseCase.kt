package com.aos.domain.usecase.user.signup

import com.aos.domain.model.user.signup.ImageProfileModel
import com.aos.domain.repository.user.UserRepository
import java.io.File
import javax.inject.Inject

class PostImageProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        file: File
    ): Result<ImageProfileModel> {
        return userRepository.postImageProfile(file)
    }
}