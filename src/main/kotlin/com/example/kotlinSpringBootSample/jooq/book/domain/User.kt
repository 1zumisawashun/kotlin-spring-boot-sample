package com.example.kotlinSpringBootSample.jooq.book.domain

enum class RoleType {
    ADMIN,
    USER
}

data class User(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val roleType: RoleType,
)

interface UserRepository {
    fun find(email: String): User?
//    fun findAll(): List<User>
//    fun findById(id: Int): User?
//    fun findByEmail(email: String): User?
//    fun register(user: User)
//    fun update(id: Int, email: String?, password: String?, name: String?, roleType: RoleType?)
//    fun delete(id: Int)
}
