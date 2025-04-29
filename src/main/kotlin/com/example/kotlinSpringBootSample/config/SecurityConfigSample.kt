package com.example.kotlinSpringBootSample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

/**
 * ▼ 以下を参考にしている
 * https://docs.spring.io/spring-security/reference/servlet/configuration/kotlin.html
 * https://github.com/spring-projects/spring-security-samples/blob/main/servlet/spring-boot/kotlin/hello-security/src/main/kotlin/org/springframework/security/samples/config/SecurityConfig.kt
 */

/**
 * ▼ import org.springframework.security.config.annotation.web.invoke
 * Make sure to import the org.springframework.security.config.annotation.web.invoke function to enable the Kotlin DSL in your class, as the IDE will not always auto-import the method, causing compilation issues.
 */

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): InMemoryUserDetailsManager {
        val userDetails = User
            .withUsername("user")
            .password(passwordEncoder().encode("123456"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(userDetails)
    }
}
