package com.aos.data.dto.activity.home

import com.aos.domain.model.activity.home.ActivityScheduleModel
import kotlinx.serialization.Serializable

@Serializable
data class GetActivityScheduleEntity(
    val response: GetActivityScheduleResponse,
)

@Serializable
data class GetActivityScheduleResponse(
    val activities: List<Activity>,
)

fun GetActivityScheduleEntity.toActivityScheduleModel(): ActivityScheduleModel {
    return ActivityScheduleModel(
        this.response.activities.map {
            com.aos.domain.model.activity.home.Activity(
                dday = it.dday,
                id= it.id,
                location= it.location,
                startDay= it.startDay,
                title= it.title
            )
        }
    )
}