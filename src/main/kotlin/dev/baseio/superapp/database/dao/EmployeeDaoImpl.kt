package dev.baseio.superapp.database.dao

import dev.baseio.superapp.database.entities.Employee
import dev.baseio.superapp.database.entities.employees
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.any
import org.ktorm.entity.find
import org.ktorm.support.postgresql.insertOrUpdateReturning
import java.time.LocalDate
import java.util.*

class EmployeeDaoImpl(private val database: Database) : EmployeeDao {
    override fun createUpdateEmployee(
        id: UUID?,
        name: String,
        job: String,
        managerId: UUID,
        hireDate: LocalDate,
        departmentId: UUID,
        location: UUID
    ): Employee? {
        val uuid = database.insertOrUpdateReturning(Employee, Employee.id) {
            id?.let {
                this.set(Employee.id, id)
            }
            this.set(Employee.name, name)
            this.set(Employee.job, job)
            this.set(Employee.managerId, managerId)
            this.set(Employee.hireDate, hireDate)
            this.set(Employee.departmentId, departmentId)
            this.set(Employee.location, location)
        }
        uuid?.let {
            return database.employees.find { it.id eq uuid }
        }
        return null
    }

    override fun findByUUID(uuid: UUID): Employee? {
        return database.employees.find { it.id eq uuid }
    }

    override fun exists(uuid: UUID): Boolean {
        return database.employees.any { it.id eq uuid }
    }
}