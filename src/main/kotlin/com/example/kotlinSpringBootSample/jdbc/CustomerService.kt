package com.example.kotlinSpringBootSample.jdbc

import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {
    override fun insertCustomer(firstName: String, lastName: String) {
        customerRepository.add(firstName, lastName)
        return
    }

    override fun selectCustomer(): List<Customer> {
        return customerRepository.find()
    }

    override fun updateCustomer(id: Int, firstName: String, lastName: String) {
        customerRepository.update(id, firstName, lastName)
        return
    }

    override fun deleteCustomer(id: Int) {
        customerRepository.delete(id)
        return
    }
}
