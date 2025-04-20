package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.Book
import com.example.kotlinSpringBootSample.jooq.book.domain.BookRepository
import com.example.kotlinSpringBootSample.jooq.book.domain.BookWithRental
import com.example.kotlinSpringBootSample.jooq.book.domain.Rental
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Book.BOOK
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Rental.RENTAL
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Users.USERS
import com.example.kotlinSpringBootSample.jooq.jooq.tables.records.BookRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class BookRepositoryImpl(
    private val dsl: DSLContext
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        val record = dsl.select().from(BOOK)
            .leftJoin(USERS)
            .on(USERS.ID.eq(BOOK.ID))
            .leftJoin(RENTAL)
            .on(RENTAL.BOOK_ID.eq(BOOK.ID))
            .fetch()

        return record.map { toModel(it) }
    }

    override fun findWithRental(id: Int): BookWithRental? {
        val record = dsl.select().from(BOOK)
            .leftJoin(USERS)
            .on(USERS.ID.eq(BOOK.ID))
            .leftJoin(RENTAL)
            .on(RENTAL.BOOK_ID.eq(BOOK.ID))
            .where(BOOK.ID.eq(id))
            .fetchOne()

        return record?.let { toModel(it) }
    }

    override fun register(book: Book) {
        dsl.insertInto(BOOK)
            .set(BOOK.ID, book.id)
            .set(BOOK.TITLE, book.title)
            .set(BOOK.AUTHOR, book.author)
            .set(BOOK.RELEASE_DATE, book.releaseDate)
            .execute()
    }

    override fun update(id: Int, title: String?, author: String?, releaseDate: LocalDate?) {
        dsl.update(BOOK)
            .set(BOOK.ID, id)
            .set(BOOK.TITLE, title)
            .set(BOOK.AUTHOR, author)
            .set(BOOK.RELEASE_DATE, releaseDate)
            .where(BOOK.ID.eq(id))
            .execute()
    }

    override fun delete(id: Int) {
        dsl.deleteFrom(BOOK)
            .where(BOOK.ID.eq(id))
            .execute()
    }

    private fun toRecord(model: Book): BookRecord {
        return BookRecord(model.id, model.title, model.author, model.releaseDate)
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
