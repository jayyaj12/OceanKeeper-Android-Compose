package com.aos.data.repository.user

import com.aos.data.datasource.user.UserRemoteDataSourceImpl
import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.toLoginModel
import com.aos.data.dto.user.signup.SignUpReqDto
import com.aos.data.dto.user.signup.toAuthSignUpModel
import com.aos.data.dto.user.signup.toImageProfileModel
import com.aos.data.network.exception.RetrofitFailureStateException
import com.aos.data.network.state.NetworkState
import com.aos.domain.model.user.login.LoginModel
import com.aos.domain.model.user.signup.AuthSignUpModel
import com.aos.domain.model.user.signup.ImageProfileModel
import com.aos.domain.repository.user.UserRepository
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSourceImpl: UserRemoteDataSourceImpl) :
    UserRepository {
    override suspend fun postLogin(
        deviceToken: String,
        provider: String,
        providerId: String,
    ): Result<LoginModel> {
        return when (val data = userRemoteDataSourceImpl.postLogin(
            LoginReqDto(
                deviceToken, provider, providerId
            )
        )) {
            is NetworkState.Success -> Result.success(data.body.toLoginModel())
            is NetworkState.Failure -> Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun postImageProfile(file: File): Result<ImageProfileModel> {
        return when (val data = userRemoteDataSourceImpl.postImageProfile(
            file
        )) {
            is NetworkState.Success -> Result.success(data.body.toImageProfileModel())
            is NetworkState.Failure -> Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun postAuthSignup(
        deviceToken: String,
        email: String,
        nickname: String,
        profile: String,
        provider: String,
        providerId: String,
    ): Result<AuthSignUpModel> {
        return when (val data = userRemoteDataSourceImpl.postAuthSignup(
            SignUpReqDto(
                deviceToken,email, nickname, profile, provider, providerId
            )
        )) {
            is NetworkState.Success -> Result.success(data.body.toAuthSignUpModel())
            is NetworkState.Failure -> Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }
}