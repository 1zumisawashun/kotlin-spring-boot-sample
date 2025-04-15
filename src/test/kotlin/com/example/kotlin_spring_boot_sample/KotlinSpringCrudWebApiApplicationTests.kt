package com.example.kotlin_spring_boot_sample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KotlinSpringCrudWebApiApplicationTests(@Autowired val mockMvc: MockMvc) {
    @Test
    @DisplayName("get /customers のテスト")
    fun read() {
        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/customers")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().response
        val actualStatus = response.status
        println(actualStatus)
        val actualResponseBody = response.contentAsString

        val expectedStatus = HttpStatus.OK.value()
        val expectedResponseBody =
            """
               {
                 "customers": [
                   {
                     "id": 1,
                     "firstName": "Alice",
                     "lastName": "Sample1"
                   },
                   {
                     "id": 2,
                     "firstName": "Bob",
                     "lastName": "Sample2"
                   }
                 ]
               }
            """.trimIndent()
        assertThat(actualStatus).isEqualTo(expectedStatus)
        JSONAssert.assertEquals(
            expectedResponseBody,
            actualResponseBody,
            JSONCompareMode.NON_EXTENSIBLE
        )
    }
}
