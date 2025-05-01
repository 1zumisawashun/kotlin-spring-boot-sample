package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.Book
import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import com.example.kotlinSpringBootSample.jooq.book.domain.Rental
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("book")
@CrossOrigin
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        println("一覧")
        val bookList = bookService.getList().map {
            BookInfo(it)
        }
        return GetBookListResponse(bookList)
    }

    @GetMapping("/detail/{book_id}")
    fun getDetail(@PathVariable("book_id") bookId: Int): GetBookDetailResponse {
        println("詳細")
        val book = bookService.getDetail(bookId)
        return GetBookDetailResponse(book)
    }

   @PostMapping("/register")
    fun register(@RequestBody request: RegisterBookRequest) {
        println("登録")
        bookService.register(
            Book(
                request.id,
                request.title,
                request.author,
                request.releaseDate // NOTE: release_dateだと404になる
            )
        )
    }

    @PutMapping("/update")
    fun update(@RequestBody request: UpdateBookRequest) {
        println("更新")
        bookService.update(request.id, request.title, request.author, request.releaseDate)
    }

    @DeleteMapping("/delete/{book_id}")
    fun delete(@PathVariable("book_id") bookId: Int) {
        println("削除")
        bookService.delete(bookId)
    }
}

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

data class RegisterBookRequest(
    val id: Int,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)

data class UpdateBookRequest(
    val id: Int,
    val title: String?,
    val author: String?,
    val releaseDate: LocalDate?
)
