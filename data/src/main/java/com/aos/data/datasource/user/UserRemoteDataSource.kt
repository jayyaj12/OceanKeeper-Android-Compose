package com.aos.data.datasource.user

import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.network.state.NetworkState

interface UserRemoteDataSource {

    suspend fun postLogin(loginReqDto: LoginReqDto): NetworkState<PostLoginEntity>

}