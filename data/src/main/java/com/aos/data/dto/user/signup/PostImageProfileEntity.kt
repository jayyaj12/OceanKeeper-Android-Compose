package com.aos.data.dto.user.signup

import com.aos.domain.model.user.signup.ImageProfileModel
import kotlinx.serialization.Serializable

@Serializable
data class PostImageProfileEntity(
    val url: String
)

fun PostImageProfileEntity.toImageProfileModel(): ImageProfileModel{
    return ImageProfileModel(this.url)
}
