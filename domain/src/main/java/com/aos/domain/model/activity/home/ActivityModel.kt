package com.aos.domain.model.activity.home

data class ActivityModel(
    val activityList: List<ActivityItem>,
    val isLast: Boolean
)

data class ActivityItem(
    val activityId: String,
    val activityImageUrl: String,
    val activityStatus: String,
    val garbageCategory: String,
    val hostNickname: String,
    val location: String,
    val locationTag: String,
    val participants: Int,
    val quota: Int,
    val recruitEndAt: String,
    val recruitStartAt: String,
    val recruitmentStarted: Boolean,
    val rewards: String,
    val startAt: String,
    val title: String
)