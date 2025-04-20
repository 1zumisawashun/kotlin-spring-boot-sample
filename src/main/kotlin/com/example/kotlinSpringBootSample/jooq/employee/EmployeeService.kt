package com.example.kotlinSpringBootSample.jooq.employee

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeServiceImpl(private val employeeRepository: EmployeeRepository) : EmployeeService {

    override fun getEmployee(): List<Employee> {
        return employeeRepository.find()
    }

    override fun getEmployeeById(id: Int): Employee? {
        println(id)
        return employeeRepository.findById(id)
    }

    @Transactional
    override fun createEmployee(employee: Employee) {
        println(employee)
        employeeRepository.save(employee)
    }
}
