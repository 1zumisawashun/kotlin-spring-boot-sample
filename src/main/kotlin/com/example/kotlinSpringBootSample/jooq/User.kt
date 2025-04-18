package com.example.kotlinSpringBootSample.jooq

data class User(val id: Int, val name: String)

interface UserRepository {
    fun find(): List<User>
    fun findById(id: Int): User?
    fun save(user: User)
}

interface UserService {
    fun getUser(): List<User>
    fun getUserById(id: Int): User?
    fun createUser(user: User)
}
