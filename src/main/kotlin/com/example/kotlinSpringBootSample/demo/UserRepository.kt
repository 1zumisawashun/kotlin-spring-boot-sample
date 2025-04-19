package com.example.kotlinSpringBootSample.demo

import com.example.kotlinSpringBootSample.demo.jooq.tables.Users.USERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val dsl: DSLContext) : UserRepository {

    override fun find(): List<User> {
        val record = dsl.selectFrom(USERS).fetch()
        return record.map { user -> User(id = user.id, name = user.name) }
    }

    override fun findById(id: Int): User? {
        val record = dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOne()

        return record?.let {
            User(it.id, it.name)
        }
    }

    override fun save(user: User) {
        val result = dsl.insertInto(USERS)
            .set(USERS.ID, user.id)
            .set(USERS.NAME, user.name)
            .execute()

        println(result)
    }
}
