package dev.baseio.superapp.database.entities

import org.ktorm.schema.*

class Employee : Table<Nothing>("t_users") {
    val id = int("id").primaryKey()
    val name = varchar("name")
    val job = varchar("job")
    val managerId = int("manager_id")
    val hireDate = date("hire_date")
    val departmentId = int("department_id")
    val location = varchar("location")
}