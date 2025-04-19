package com.example.kotlinSpringBootSample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringBootSampleApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringBootSampleApplication>(*args)
}
