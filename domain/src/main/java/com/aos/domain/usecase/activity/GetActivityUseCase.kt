package com.aos.domain.usecase.activity

import com.aos.domain.model.activity.home.ActivityModel
import com.aos.domain.model.activity.home.ActivityScheduleModel
import com.aos.domain.repository.activity.ActivityRepository
import javax.inject.Inject

class GetActivityUseCase @Inject constructor(private val activityRepository: ActivityRepository) {

    suspend operator fun invoke(
        activityId: String?,
        garbageCategory: String?,
        locationTag: String?,
        size: Int?,
        status: String?,
    ): Result<ActivityModel> {
        return activityRepository.getActivity(
            activityId,
            garbageCategory,
            locationTag,
            size,
            status
        )
    }

}