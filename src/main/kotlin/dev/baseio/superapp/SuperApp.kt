package dev.baseio.superapp

import dev.baseio.superapp.koin.DatabaseComponent
import dev.baseio.superapp.koin.databaseModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.baseio.superapp.plugins.*
import io.ktor.application.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    authentication()
    configureStatusPages()
    configureRouting()
    configureHTTP()
    configureTemplating()
    configureSerialization()
    // Declare Koin
    install(Koin) {
        SLF4JLogger()
        modules(databaseModule(environment.config))
    }
    databaseComponent.provideDatabaseConnectionService().connect()
}

val databaseComponent by lazy { DatabaseComponent() }

inline fun <reified T> getKoinInstance() =
    object : KoinComponent {
        val value: T by inject()
    }.value