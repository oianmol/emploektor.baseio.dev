package dev.baseio.superapp.database.dao

import dev.baseio.superapp.database.entities.Employee
import java.time.LocalDate
import java.util.*

interface EmployeeDao {
    fun createUpdateEmployee(
        id: UUID?,
        name: String,
        job: String,
        managerId: UUID,
        hireDate: LocalDate,
        departmentId: UUID,
        location: UUID,
    ): Employee?

    fun findByUUID(uuid: UUID): Employee?
    fun exists(uuid: UUID): Boolean
}

