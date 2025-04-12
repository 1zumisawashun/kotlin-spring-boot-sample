package com.example.kotlin_spring_boot_sample.demo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserById(id: Int): User? {
        println(id)
        return userRepository.findById(id)
    }

    @Transactional
    fun createUser(user: User) {
        println(user)
        userRepository.save(user)
    }
}
