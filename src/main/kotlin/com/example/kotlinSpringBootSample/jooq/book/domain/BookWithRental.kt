package com.example.kotlinSpringBootSample.jooq.book.domain

import java.time.LocalDate

data class BookWithRental(
    val book: Book,
    val rental: Rental?
) {
    // 拡張プロパティ
    val isRental: Boolean
        get() = rental != null
}

interface BookRepository {
    fun findAllWithRental(): List<BookWithRental>
    fun findWithRental(id: Int): BookWithRental?
    fun register(book: Book)
    fun update(id: Int, title: String?, author: String?, releaseDate: LocalDate?)
    fun delete(id: Int)
}
