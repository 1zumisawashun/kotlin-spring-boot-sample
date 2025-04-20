package com.example.kotlinSpringBootSample.jooq.book

import com.example.kotlinSpringBootSample.jooq.book.domain.*
import com.example.kotlinSpringBootSample.jooq.jooq.tables.Users.USERS
import com.example.kotlinSpringBootSample.jooq.jooq.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

// TODO: 認証認可周りは構造で対応する

@Repository
class UserRepositoryImpl(
    private val dsl: DSLContext
) : UserRepository {
    override fun find(email: String): User? {
        val record = dsl.selectFrom(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOne()

        return record?.let { toModel(it) }
    }

    private fun toModel(record: UsersRecord): User {
        return User(
            record.id!!,
            record.email!!,
            record.password!!,
            record.name!!,
            RoleType.valueOf(record.roleType!!),
            createdAt = "",
            updatedAt = "",
        )

    }
}
