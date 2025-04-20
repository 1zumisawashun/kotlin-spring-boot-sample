package com.example.kotlinSpringBootSample.jooq.employee

import com.example.kotlinSpringBootSample.jooq.jooq.tables.Employee.EMPLOYEE
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class EmployeeRepositoryImpl(private val dsl: DSLContext) : EmployeeRepository {

    override fun find(): List<Employee> {
        val record = dsl.selectFrom(EMPLOYEE).fetch()
        return record.map { employee -> Employee(id = employee.id, name = employee.name) }
    }

    override fun findById(id: Int): Employee? {
        val record = dsl.selectFrom(EMPLOYEE)
            .where(EMPLOYEE.ID.eq(id))
            .fetchOne()

        return record?.let {
            Employee(it.id, it.name)
        }
    }

    override fun save(employee: Employee) {
        val result = dsl.insertInto(EMPLOYEE)
            .set(EMPLOYEE.ID, employee.id)
            .set(EMPLOYEE.NAME, employee.name)
            .execute()

        println(result)
    }
}
