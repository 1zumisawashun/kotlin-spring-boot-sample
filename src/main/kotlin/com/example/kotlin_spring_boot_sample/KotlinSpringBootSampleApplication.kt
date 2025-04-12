package com.example.kotlin_spring_boot_sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringBootSampleApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringBootSampleApplication>(*args)
}
