package dev.baseio.superapp.database.connection

import org.ktorm.database.Database

interface DatabaseConnectionService {
    fun connect(): Database
}