package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.Book
import com.example.kotlinSpringBootSample.jooq.book.domain.BookRepository
import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> {
        return bookRepository.findAllWithRental()
    }
    fun getDetail(bookId: Int): BookWithRental {
        // MEMO: エルビス演算子とスコープ関数の違いは？
        return bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
    }

    @Transactional
    fun register(book: Book) {
        bookRepository.findWithRental(book.id)?.let { throw IllegalArgumentException("既に存在する書籍ID: ${book.id}") }
        bookRepository.register(book)
    }

    @Transactional
    fun update(bookId: Int, title: String?, author: String?, releaseDate: LocalDate?) {
        bookRepository.findWithRental(bookId) ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
        bookRepository.update(bookId, title, author, releaseDate)
    }
}
