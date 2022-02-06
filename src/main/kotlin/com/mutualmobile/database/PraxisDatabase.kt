package com.mutualmobile.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PraxisDatabase {
    fun initialize() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver",
            user = "postgres", password = "postgres"
        )
        transaction {
            addLogger(StdOutSqlLogger)
        }
    }
}