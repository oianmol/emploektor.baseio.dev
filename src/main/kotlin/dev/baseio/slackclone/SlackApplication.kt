package dev.baseio.slackclone

import dev.baseio.slackclone.database.SlackDatabase
import dev.baseio.slackclone.koin.databaseModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.baseio.slackclone.plugins.*
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        authentication()
        configureStatusPages()
        configureRouting()
        configureHTTP()
        configureTemplating()
        configureSerialization()
        // Declare Koin
        install(Koin) {
            SLF4JLogger()
            modules(databaseModule)
        }
        val database: SlackDatabase by inject()
        database.initializeOnStart(this)

    }.start(wait = true)
}
