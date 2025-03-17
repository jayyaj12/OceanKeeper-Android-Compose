package com.aos.data.datasource.activity

import com.aos.data.dto.activity.home.GetActivityScheduleEntity
import com.aos.data.network.api.OceanService
import com.aos.data.network.state.NetworkState
import javax.inject.Inject


class ActivityRemoteDataSourceImpl @Inject constructor(private val oceanService: OceanService): ActivityRemoteDataSource {

    override suspend fun getActivitySchedule(userId: String): NetworkState<GetActivityScheduleEntity> {
        return oceanService.getActivitySchedule(userId)
    }
}