package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.Book
import com.example.kotlinSpringBootSample.jooq.book.domain.BookRepository
import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import com.example.kotlinSpringBootSample.jooq.book.domain.Rental
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Book.BOOK
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Rental.RENTAL
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Users.USERS
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val dsl: DSLContext
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        val record = dsl.select().from(
            BOOK
        ).leftJoin(USERS).on(USERS.ID.eq(BOOK.ID)).leftJoin(RENTAL).on(RENTAL.BOOK_ID.eq(BOOK.ID)).fetch()
        println(record)
        return record.map { toModel(it) }
    }

    override fun findWithRental(id: Int): BookWithRental? {
        val record = dsl.select().from(
            BOOK
        ).leftJoin(
            USERS
        ).on(USERS.ID.eq(BOOK.ID)).leftJoin(RENTAL).on(RENTAL.BOOK_ID.eq(BOOK.ID)).where(BOOK.ID.eq(id)).fetchOne()
        println(record)
        return record?.let { toModel(it) }
    }

    private fun toModel(record: Record): BookWithRental {
        val book = Book(
            id = record.get(BOOK.ID),
            title = record.get(BOOK.TITLE)!!,
            author = record.get(BOOK.AUTHOR)!!,
            releaseDate = record.get(BOOK.RELEASE_DATE)!!
        )
        val rental = record.get(USERS.ID)?.let {
            Rental(
                bookId = record.get(BOOK.ID)!!,
                userId = it,
                rentalDatetime = record.get(RENTAL.RENTAL_DATETIME),
                returnDeadline = record.get(RENTAL.RETURN_DEADLINE)
            )
        }
        return BookWithRental(book, rental)
    }
}
