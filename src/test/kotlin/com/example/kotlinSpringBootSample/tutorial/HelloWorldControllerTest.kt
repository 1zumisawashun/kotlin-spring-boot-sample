package com.example.kotlinSpringBootSample.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloWorldControllerTest {
    @Test
    fun helloWorld() {
        val sut = HelloWorldController()
        val actual = sut.helloWorld()
        val expected = "Hello World"
        assertEquals(actual, expected)
    }
}
