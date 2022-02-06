package dev.baseio.slackclone.database

import dev.baseio.slackclone.database.entity.SlackUser
import io.r2dbc.spi.Row

object DB {
    fun ofUser(r: Row, meta: Any?) = SlackUser(
        name = r.get("name", String::class.java),
        email = r.get("email", String::class.java),
        picurl = r.get("picurl,String::class.java"),
        department = r.get("department,String::class.java"),
        title = r.get("title", String::class.java),
        username = r.get("username", String::class.java),
        online = r.get("online", Boolean::class.java),
    )
}