package dev.baseio.slackclone

import dev.baseio.slackclone.database.PraxisDatabase
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.baseio.slackclone.plugins.*
import dev.baseio.slackclone.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        authentication()
        configureStatusPages()
        configureRouting()
        configureHTTP()
        configureTemplating()
        configureSerialization()
        PraxisDatabase().initialize()
    }.start(wait = true)
}
