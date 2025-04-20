package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.BookRepository
import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }
    fun getDetail(bookId: Int): BookWithRental {
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
    }
}
