package dev.baseio.superapp

import dev.baseio.superapp.koin.databaseModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.baseio.superapp.plugins.*
import io.ktor.application.*
import org.koin.ktor.ext.Koin
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
    }.start(wait = true)
}
