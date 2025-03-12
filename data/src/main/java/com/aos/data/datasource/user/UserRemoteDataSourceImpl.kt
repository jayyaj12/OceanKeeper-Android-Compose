package com.aos.data.datasource.user

import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.network.api.OceanService
import com.aos.data.network.state.NetworkState
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val oceanService: OceanService): UserRemoteDataSource {

    override suspend fun postLogin(loginReqDto: LoginReqDto): NetworkState<PostLoginEntity> {
        return oceanService.postLogin(loginReqDto)
    }

}