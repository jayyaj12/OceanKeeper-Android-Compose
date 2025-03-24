package com.aos.data.dto.activity.home

import kotlinx.serialization.Serializable

@Serializable
data class GetActivityEntityItem(
    val activities: List<Activities>,
    val meta: Meta
)