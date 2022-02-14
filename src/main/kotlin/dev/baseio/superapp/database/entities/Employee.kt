package dev.baseio.superapp.database.entities

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

object Employee : Table<Nothing>("t_users") {
    val id = uuid("id").primaryKey()
    val name = varchar("name")
    val job = varchar("job")
    val managerId = uuid("manager_id")
    val hireDate = date("hire_date")
    val departmentId = uuid("department_id")
    val location = uuid("location")
}

val Database.employees get() = this.sequenceOf(Employee)