package dev.baseio.superapp.rest.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: String,
    val data: T? = null
)
