package dev.baseio.superapp.database

import org.ktorm.database.Database

interface DatabaseConnectionService {
    fun connect(): Database
}