package com.example.kotlinSpringBootSample.jooq.book.domain

import java.time.LocalDate

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)
