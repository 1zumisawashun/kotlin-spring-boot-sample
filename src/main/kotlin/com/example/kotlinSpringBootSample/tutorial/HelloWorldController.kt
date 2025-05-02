package com.example.kotlinSpringBootSample.tutorial

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/")
    fun helloWorld(): String {
        println("Hello World")
        return "hello"
    }

    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        println("Health Check")
        return ResponseEntity.ok("Service is healthy")
    }
}
