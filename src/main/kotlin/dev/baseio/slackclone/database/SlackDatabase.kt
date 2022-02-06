package dev.baseio.slackclone.database

import dev.baseio.slackclone.database.entity.SlackUser
import io.ktor.application.*

interface SlackDatabase {
    fun initializeOnStart(application: Application)
    suspend fun tables(): List<SlackUser>
}