package com.aos.data.repository.user

import com.aos.data.datasource.user.UserRemoteDataSourceImpl
import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.toLoginModel
import com.aos.data.network.exception.RetrofitFailureStateException
import com.aos.data.network.state.NetworkState
import com.aos.domain.model.LoginModel
import com.aos.domain.repository.UserRepository
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
}