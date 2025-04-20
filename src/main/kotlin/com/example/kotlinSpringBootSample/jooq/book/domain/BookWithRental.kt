package com.example.kotlinSpringBootSample.jooq.book.domain

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
}
