package com.example.kotlinSpringBootSample.jooq.user

import com.example.kotlinSpringBootSample.jooq.jooq.tables.Users.USERS
import com.example.kotlinSpringBootSample.jooq.jooq.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val dsl: DSLContext
) : UserRepository {
    override fun find(email: String): User? {
        val record = dsl.selectFrom(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOne()

        println("record: $record")
        return record?.let { toModel(it) }
    }

    private fun toModel(record: UsersRecord): User {
        return User(
            record.id!!,
            record.email!!,
            record.password!!,
            record.name!!,
            RoleType.valueOf(record.roleType!!),
        )
    }
}
