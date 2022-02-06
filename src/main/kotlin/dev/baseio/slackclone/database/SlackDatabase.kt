package dev.baseio.slackclone.database

import dev.baseio.slackclone.database.entity.SlackUser
import io.ktor.application.*

interface SlackDatabase {
    fun initializeOnStart(application: Application)
    suspend fun getUsers(query: String): List<SlackUser>
}