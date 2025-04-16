package com.aos.data.datasource.activity

import com.aos.data.dto.activity.home.GetActivityEntity
import com.aos.data.dto.activity.home.GetActivityScheduleEntity
import com.aos.data.network.state.NetworkState

interface ActivityRemoteDataSource {

    suspend fun getActivitySchedule(userId: String): NetworkState<GetActivityScheduleEntity>
    suspend fun getActivity(activityId: String?, garbageCategory: String?, locationTag: String?, size: Int?, status: String?): NetworkState<GetActivityEntity>

}