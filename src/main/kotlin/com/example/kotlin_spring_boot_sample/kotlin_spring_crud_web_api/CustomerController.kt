package com.example.kotlin_spring_boot_sample.kotlin_spring_crud_web_api

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(val customerService: CustomerService) {
    @PostMapping("/customers")
    fun insert(@RequestBody request: CustomerRequest): String {
        customerService.insertCustomer(request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    @GetMapping("/customers")
    fun read(): CustomerResponse {
        return CustomerResponse(customers = customerService.selectCustomer())
    }

    @PutMapping("/customers/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody request: CustomerRequest): String {
        customerService.updateCustomer(id, request.firstName, request.lastName)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable("id") id: Int): String {
        customerService.deleteCustomer(id)
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }
}

data class CustomerRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)

data class CustomerResponse(
    val customers: List<Customer>,
)
