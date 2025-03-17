package com.aos.data.dto.activity.home

import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val dday: Int,
    val id: String,
    val location: String,
    val startDay: String,
    val title: String
)