package com.aos.data.datasource.activity

import com.aos.data.dto.activity.home.GetActivityScheduleEntity
import com.aos.data.network.state.NetworkState

interface ActivityRemoteDataSource {

    suspend fun getActivitySchedule(userId: String): NetworkState<GetActivityScheduleEntity>

}