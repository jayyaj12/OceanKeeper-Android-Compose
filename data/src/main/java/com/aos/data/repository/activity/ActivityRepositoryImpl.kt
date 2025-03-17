package com.aos.data.repository.activity

import com.aos.data.datasource.activity.ActivityRemoteDataSourceImpl
import com.aos.data.dto.activity.home.toActivityScheduleModel
import com.aos.data.dto.user.signup.toImageProfileModel
import com.aos.data.network.exception.RetrofitFailureStateException
import com.aos.data.network.state.NetworkState
import com.aos.domain.model.activity.home.ActivityScheduleModel
import com.aos.domain.repository.activity.ActivityRepository
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(private val activityRemoteDataSourceImpl: ActivityRemoteDataSourceImpl): ActivityRepository {

    override suspend fun getActivitySchedule(userId: String): Result<ActivityScheduleModel> {
        return when (val data = activityRemoteDataSourceImpl.getActivitySchedule(
            userId
        )) {
            is NetworkState.Success -> Result.success(data.body.toActivityScheduleModel())
            is NetworkState.Failure -> Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

}