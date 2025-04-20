package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import com.example.kotlinSpringBootSample.jooq.book.domain.Rental
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

data class GetBookListResponse(val bookList: List<BookInfo>)

data class BookInfo(
    val id: Int,
    val title: String,
    val author: String,
    val isRental: Boolean
) {
    constructor(model: BookWithRental) : this(model.book.id, model.book.title, model.book.author, model.isRental)
}

data class GetBookDetailResponse(
    val id: Int,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo: RentalInfo?
) {
    constructor(model: BookWithRental) : this(
        model.book.id,
        model.book.title,
        model.book.author,
        model.book.releaseDate,
        model.rental?.let { RentalInfo(model.rental) }
    )
}

data class RentalInfo(
    val userId: Int,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
) {
    constructor(rental: Rental) : this(rental.userId, rental.rentalDatetime, rental.returnDeadline)
}

@RestController
@RequestMapping("book")
@CrossOrigin
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map {
            BookInfo(it)
        }
        return GetBookListResponse(bookList)
    }

    @GetMapping("/detail/{book_id}")
    fun getDetail(@PathVariable("book_id") bookId: Int): GetBookDetailResponse {
        val book = bookService.getDetail(bookId)
        return GetBookDetailResponse(book)
    }
}
