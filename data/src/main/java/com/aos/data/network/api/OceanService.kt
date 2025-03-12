package com.aos.data.network.api

import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.network.state.NetworkState
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OceanService {

    @POST("auth/login")
    suspend fun postLogin(
        @Body loginReqDto: LoginReqDto
    ): NetworkState<PostLoginEntity>

}