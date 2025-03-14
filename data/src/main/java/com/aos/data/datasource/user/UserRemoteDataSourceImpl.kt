package com.aos.data.datasource.user

import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.dto.user.signup.PostAuthSingUpEntity
import com.aos.data.dto.user.signup.PostImageProfileEntity
import com.aos.data.dto.user.signup.SignUpReqDto
import com.aos.data.network.api.OceanService
import com.aos.data.network.state.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val oceanService: OceanService): UserRemoteDataSource {

    override suspend fun postLogin(loginReqDto: LoginReqDto): NetworkState<PostLoginEntity> {
        return oceanService.postLogin(loginReqDto)
    }

    override suspend fun postImageProfile(file: File): NetworkState<PostImageProfileEntity> {
        val requestBody: RequestBody =
            file.asRequestBody("image/*".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData("profile", "profile.jpg", requestBody)

        return oceanService.postImageProfile(imageBody)
    }

    override suspend fun postAuthSignup(signUpReqDto: SignUpReqDto): NetworkState<PostAuthSingUpEntity> {
        return oceanService.postAuthSignUp(signUpReqDto)
    }

}