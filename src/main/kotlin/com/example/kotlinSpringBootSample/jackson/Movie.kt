package com.example.kotlinSpringBootSample.jackson

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * PropertyNamingStrategies が有効にならないな...
 */
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SnakeCaseDTO(
    val someField: String? = null,
    private val anotherField: String? = null
)

@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy::class)
data class UpperCamelCaseDTO(
    val someField: String? = null,
    private val anotherField: String? = null
)

fun main(args: Array<String>) {
    val snakeCaseDTO = SnakeCaseDTO()
    println(snakeCaseDTO)
    val upperCamelCaseDTO = UpperCamelCaseDTO()
    println(upperCamelCaseDTO)
}
