package com.example.kotlin_spring_boot_sample.kotlin_spring_crud_web_api

data class Customer(
    val id: Long,
    val firstName: String,
    val lastName: String,
)

interface CustomerService {
    fun insertCustomer(firstName: String, lastName: String)
    fun selectCustomer(): List<Customer>
    fun updateCustomer(id: Int, firstName: String, lastName: String)
    fun deleteCustomer(id: Int)
}

interface CustomerRepository {
    fun add(firstName: String, lastName: String)
    fun find(): List<Customer>
    fun update(id: Int, firstName: String, lastName: String)
    fun delete(id: Int)
}
