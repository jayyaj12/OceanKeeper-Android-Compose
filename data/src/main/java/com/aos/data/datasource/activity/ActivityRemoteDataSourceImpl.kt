package com.aos.data.datasource.activity

import com.aos.data.dto.activity.home.GetActivityEntity
import com.aos.data.dto.activity.home.GetActivityScheduleEntity
import com.aos.data.network.api.OceanService
import com.aos.data.network.state.NetworkState
import javax.inject.Inject


class ActivityRemoteDataSourceImpl @Inject constructor(private val oceanService: OceanService): ActivityRemoteDataSource {

    override suspend fun getActivitySchedule(userId: String): NetworkState<GetActivityScheduleEntity> {
        return oceanService.getActivitySchedule(userId)
    }

    override suspend fun getActivity(
        activityId: String?,
        garbageCategory: String?,
        locationTag: String?,
        size: Int?,
        status: String?,
    ): NetworkState<GetActivityEntity> {
        return oceanService.getActivity(activityId, garbageCategory, locationTag, size, status)
    }
}