package dev.baseio.superapp.database

import dev.baseio.superapp.Config
import org.ktorm.database.Database

class DatabaseConnectionServiceImpl(private val databaseConfig: Config) : DatabaseConnectionService {
    override fun connect(): Database {
        return Database.connect(
            url = with(databaseConfig) { "jdbc:postgresql://$dbHost:$dbPort/$dbName" },
            driver = "org.postgresql.Driver",
            user = databaseConfig.dbUser,
            password = databaseConfig.dbPassword
        )
    }
}