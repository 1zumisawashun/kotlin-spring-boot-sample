package com.example.kotlin_spring_boot_sample.demo

data class User(val id: Int, val name: String)

interface UserRepository {
    fun findById(id: Int): User?
    fun save(user: User)
}