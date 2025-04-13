package com.example.kotlin_spring_boot_sample.demo

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getUser(): List<User> {
        return userRepository.find()
    }

    override fun getUserById(id: Int): User? {
        println(id)
        return userRepository.findById(id)
    }

    @Transactional
    override fun createUser(user: User) {
        println(user)
        userRepository.save(user)
    }
}
