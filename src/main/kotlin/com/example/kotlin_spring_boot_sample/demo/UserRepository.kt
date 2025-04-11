package com.example.kotlin_spring_boot_sample.demo

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import com.example.kotlin_spring_boot_sample.demo.jooq.tables.Customer.CUSTOMER

@Repository
class UserRepository(private val dsl: DSLContext): IUserRepository {

    override fun findById(id: Int): User? {
        val record = dsl.selectFrom(CUSTOMER)
            .where(CUSTOMER.ID.eq(id))
            .fetchOne()

        return record?.let {
            User(it.id, it.firstName)
        }
    }

    override fun save(user: User) {
        val result = dsl.insertInto(CUSTOMER)
            .set(CUSTOMER.ID, user.id)
            .set(CUSTOMER.FIRST_NAME, user.name)
            .execute()
    }
}