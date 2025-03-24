package com.aos.data.network.api

import com.aos.data.dto.activity.home.GetActivityEntity
import com.aos.data.dto.activity.home.GetActivityScheduleEntity
import com.aos.data.dto.user.login.LoginReqDto
import com.aos.data.dto.user.login.PostLoginEntity
import com.aos.data.dto.user.signup.PostAuthSingUpEntity
import com.aos.data.dto.user.signup.PostImageProfileEntity
import com.aos.data.dto.user.signup.SignUpReqDto
import com.aos.data.network.state.NetworkState
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface OceanService {
    /** 로그인. */
    // 로그인 요청
    @POST("auth/login")
    suspend fun postLogin(
        @Body loginReqDto: LoginReqDto,
    ): NetworkState<PostLoginEntity>

    /** 회원가입 */
    // 회원가입 프로필 등록
    @Multipart
    @POST("image/profile")
    suspend fun postImageProfile(
        @Part profile: MultipartBody.Part,
    ): NetworkState<PostImageProfileEntity>

    // 회원가입
    @POST("auth/signup")
    suspend fun postAuthSignUp(
        @Body signUpReqDto: SignUpReqDto,
    ): NetworkState<PostAuthSingUpEntity>

    /** 활동 조회 */
    // 다가오는 일정 조회
    @GET("activity/schedule/user/{userId}")
    suspend fun getActivitySchedule(
        @Path("userId") userId: String
    ): NetworkState<GetActivityScheduleEntity>

    // 활동 목록 조회
    @GET("activity")
    suspend fun getActivity(
        @Query("activity-id") activityId: String?,
        @Query("garbage-Category") garbageCategory: String?,
        @Query("location-tag") locationTag: String?,
        @Query("size") size: Int?,
        @Query("status") status: String?
    ): NetworkState<GetActivityEntity>

}