package com.example.kotlin_spring_boot_sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import com.example.kotlin_spring_boot_sample.demo.User
import com.example.kotlin_spring_boot_sample.demo.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KotlinSpringBootSampleApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringBootSampleApplication>(*args)
}
