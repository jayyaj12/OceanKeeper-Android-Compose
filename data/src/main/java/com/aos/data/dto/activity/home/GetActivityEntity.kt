package com.aos.data.dto.activity.home

import com.aos.domain.model.activity.home.ActivityItem
import com.aos.domain.model.activity.home.ActivityModel
import kotlinx.serialization.Serializable

@Serializable
data class GetActivityEntity(
    val response: GetActivityEntityItem
)

fun GetActivityEntity.toActivityModel(): ActivityModel {
    val list = this.response.activities.map {
        ActivityItem(
            activityId = it.activityId,
            activityImageUrl = it.activityImageUrl,
            activityStatus = it.activityStatus,
            garbageCategory = it.garbageCategory,
            hostNickname = it.hostNickname,
            location = it.location,
            locationTag = it.locationTag,
            participants = it.participants,
            quota = it.quota,
            recruitEndAt = it.recruitEndAt,
            recruitStartAt = it.recruitStartAt,
            recruitmentStarted = it.recruitmentStarted,
            rewards = it.rewards,
            startAt = it.startAt,
            title = it.title
        )
    }

    return ActivityModel(
        activityList = list,
        isLast = this.response.meta.last
    )
}