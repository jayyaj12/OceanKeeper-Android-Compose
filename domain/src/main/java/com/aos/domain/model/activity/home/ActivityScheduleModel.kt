package com.aos.domain.model.activity.home

data class ActivityScheduleModel(
    val activities: List<Activity>
)

data class Activity(
    val dday: Int,
    val id: String,
    val location: String,
    val startDay: String,
    val title: String
)

