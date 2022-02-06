package dev.baseio.slackclone.rest.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: String,
    val data: T? = null
)
