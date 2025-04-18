package com.example.kotlinSpringBootSample.tutorial

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/")
    @Suppress("FunctionOnlyReturningConstant")
    fun helloWorld(): String {
        return "Hello World"
    }
}
