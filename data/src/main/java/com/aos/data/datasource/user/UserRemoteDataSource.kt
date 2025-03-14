package com.aos.data.datasource.user

import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.dto.user.signup.PostAuthSingUpEntity
import com.aos.data.dto.user.signup.PostImageProfileEntity
import com.aos.data.dto.user.signup.SignUpReqDto
import com.aos.data.network.state.NetworkState
import java.io.File

interface UserRemoteDataSource {

    suspend fun postLogin(loginReqDto: LoginReqDto): NetworkState<PostLoginEntity>
    suspend fun postImageProfile(file: File): NetworkState<PostImageProfileEntity>
    suspend fun postAuthSignup(signUpReqDto: SignUpReqDto): NetworkState<PostAuthSingUpEntity>
}