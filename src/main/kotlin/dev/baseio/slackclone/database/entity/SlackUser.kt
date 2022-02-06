package dev.baseio.slackclone.database.entity

data class SlackUser(
    val name: String?,
    val email: String?,
    val picurl: Any?,
    val department: Any?,
    val title: Any?,
    val online: Boolean?,
    val username: String?
)