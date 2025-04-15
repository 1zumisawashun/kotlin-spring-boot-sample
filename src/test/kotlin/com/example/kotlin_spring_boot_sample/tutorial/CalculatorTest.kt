package com.example.kotlin_spring_boot_sample.tutorial

import org.assertj.core.api.Assertions.assertThat
// https://kotlinlang.org/api/core/kotlin-test/kotlin.test.junit5/
import org.junit.jupiter.api.Test

class CalculatorTest {
    @Test
    fun multiplyで掛け算の結果が取得できる() {
        val sut = Calculator()
        val actual = sut.multiply(x = 2, y = 3)
        val expected = 6
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun divideで割り算の結果を取得できる() {
        val sut = Calculator()
        val actual = sut.divide(x = 6, y = 3)
        val expected = 2
        assertThat(actual).isEqualTo(expected)
    }
}
