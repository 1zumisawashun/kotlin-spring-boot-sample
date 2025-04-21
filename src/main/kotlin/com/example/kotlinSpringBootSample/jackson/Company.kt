package com.example.kotlinSpringBootSample.jackson

/**
 * 以下を参考に実装した
 * https://qiita.com/greentea2smiley/items/2fcc1c59337b6e090bce
 * ------------------------------------------------------------------
 * いくつか使い方があるぽい
 * https://github.com/FasterXML/jackson-module-kotlin
 */
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

data class Company(
    @JsonProperty("company_id")
    val id: Int = 0,
    @JsonProperty("company_name")
    val name: String? = null,
    @JsonProperty("is_exist")
    val isExist: Boolean = false
)

fun readValue() {
    val json = """
            {
                "company_id": 1,
                "company_name": "ラクスパートナーズ",
                "is_exist": true
            }
        
    """.trimIndent()

    val objectMapper = ObjectMapper().registerKotlinModule()
    val company = objectMapper.readValue(json, Company::class.java)

    println(json)
    println(company)
}

fun writeValueAsString() {
    val company = Company(1, "ラクスパートナーズ", true)

    val objectMapper = jacksonObjectMapper()
    val json = objectMapper.writeValueAsString(company)

    println(json)
    println(company)
}

fun main(args: Array<String>) {
    readValue()
    writeValueAsString()
}
