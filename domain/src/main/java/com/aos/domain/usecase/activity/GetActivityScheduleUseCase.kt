package com.aos.domain.usecase.activity

import com.aos.domain.model.activity.home.ActivityScheduleModel
import com.aos.domain.repository.activity.ActivityRepository
import javax.inject.Inject

class GetActivityScheduleUseCase @Inject constructor(private val activityRepository: ActivityRepository){

    suspend operator fun invoke(
        userId: String
    ): Result<ActivityScheduleModel> {
        return activityRepository.getActivitySchedule(userId)
    }

}