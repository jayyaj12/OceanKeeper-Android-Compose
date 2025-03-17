package com.aos.domain.repository.activity

import com.aos.domain.model.activity.home.ActivityScheduleModel

interface ActivityRepository {

    suspend fun getActivitySchedule(userId: String): Result<ActivityScheduleModel>

}