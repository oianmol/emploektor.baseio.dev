package dev.baseio.superapp

import io.ktor.config.*

/**
 * Class containing Configuration values or secret key which will be provided from
 * application.conf (from environment variables).
 */
class Config constructor(config: ApplicationConfig) {
    val secretKey = config.property("key.secret").getString()
    val dbHost = config.property("database.host").getString()
    val dbPort = config.property("database.port").getString()
    val dbName = config.property("database.name").getString()
    val dbUser = config.property("database.user").getString()
    val dbPassword = config.property("database.password").getString()
}