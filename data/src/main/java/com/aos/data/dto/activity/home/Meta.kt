package com.aos.data.dto.activity.home

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val last: Boolean,
    val size: Int
)