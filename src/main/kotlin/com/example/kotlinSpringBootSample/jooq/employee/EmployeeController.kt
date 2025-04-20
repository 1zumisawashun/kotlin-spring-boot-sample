package com.example.kotlinSpringBootSample.jooq.employee

import com.example.kotlinSpringBootSample.jdbc.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(private val employeeService: EmployeeService) {
    @GetMapping("/employee")
    fun getEmployee(): List<Employee> {
        return employeeService.getEmployee()
    }

    @GetMapping("/employee/{id}")
    fun getUser(@PathVariable id: Int): Employee? {
        println(id)
        return employeeService.getEmployeeById(id)
    }

    @PostMapping("/employee")
    fun createEmployee(@RequestBody employee: Employee): Response {
        employeeService.createEmployee(employee)
        return Response(message = "success")
    }
}

data class Response(val message: String)
