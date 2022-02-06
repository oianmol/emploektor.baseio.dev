package dev.baseio.slackclone.database.entity

import kotlinx.serialization.Serializable

@Serializable
data class SlackUser(
    val name: String?,
    val email: String?,
    val picurl: String?,
    val department: String?,
    val title: String?,
    val online: Int?,
    val username: String?
)