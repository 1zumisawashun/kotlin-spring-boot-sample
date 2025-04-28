package com.example.kotlinSpringBootSample.jooq.todo

import java.time.LocalDateTime
// import org.springframework.annotation.Id
// import org.springframework.data.annotation.PersistenceConstructor

data class Todo(
    val id: Int = 0,
    var title: String,
    var description: String,
    var completed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
