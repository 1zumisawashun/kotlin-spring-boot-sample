package com.example.kotlinSpringBootSample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke

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
        val encoder = BCryptPasswordEncoder()
        println("admin: ${encoder.encode("admin")}")
        println("user: ${encoder.encode("user")}")
        return encoder
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/css/**", permitAll)
                authorize("/", permitAll)
                // authorize("/user/**", hasAuthority("ROLE_USER"))
                authorize(anyRequest, authenticated)
            }
            formLogin {
                // loginPage = "/log-in"
                permitAll
            }
        }
        return http.build()
    }



//    @Bean
//    fun userDetailsService(): InMemoryUserDetailsManager {
//        val admin = Users
//            .withUsername("admin@test.com")
//            .password(passwordEncoder().encode("admin"))
//            .roles("ADMIN")
//            .build()
//
//        val user = User
//            .withUsername("user@test.com")
//            .password(passwordEncoder().encode("user"))
//            .roles("USER")
//            .build()
//
//        return InMemoryUserDetailsManager(admin, user)
//    }

}
