package com.example.kotlinSpringBootSample.jooq.employee

data class Employee(val id: Int, val name: String)

interface EmployeeRepository {
    fun find(): List<Employee>
    fun findById(id: Int): Employee?
    fun save(employee: Employee)
}

interface EmployeeService {
    fun getEmployee(): List<Employee>
    fun getEmployeeById(id: Int): Employee?
    fun createEmployee(employee: Employee)
}
