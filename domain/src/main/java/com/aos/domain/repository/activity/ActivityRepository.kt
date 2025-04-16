package com.aos.domain.repository.activity

import com.aos.domain.model.activity.home.ActivityModel
import com.aos.domain.model.activity.home.ActivityScheduleModel

interface ActivityRepository {

    suspend fun getActivitySchedule(userId: String): Result<ActivityScheduleModel>
    suspend fun getActivity(activityId: String?, garbageCategory: String?, locationTag: String?, size: Int?, status: String?): Result<ActivityModel>

}