package com.example.kotlinSpringBootSample.jooq.book.domain

import java.time.LocalDateTime

data class Rental(
    val bookId: Int,
    val userId: Int,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime
)
