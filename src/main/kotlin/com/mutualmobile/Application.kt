package com.mutualmobile

import com.mutualmobile.database.PraxisDatabase
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.mutualmobile.plugins.*

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
