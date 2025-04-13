package com.example.kotlin_spring_boot_sample.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {
    @GetMapping("/users")
    fun getUsers(): List<User> {
        return userService.getUser()
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Int): User? {
        return userService.getUserById(id)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }
}
