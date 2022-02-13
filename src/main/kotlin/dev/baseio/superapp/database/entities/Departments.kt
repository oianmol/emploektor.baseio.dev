package dev.baseio.superapp.database.entities

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Departments : Table<Nothing>("t_department") {
    val id = int("id").primaryKey()
    val name = varchar("name")
}