package com.example.kotlinSpringBootSample.config

import com.example.kotlinSpringBootSample.jooq.book.*
import com.example.kotlinSpringBootSample.jooq.book.domain.RoleType
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke // ネストさせるために必要？
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig(private val authenticationService: AuthenticationService) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/login", permitAll)
//                authorize("/admin/**", hasAuthority(RoleType.ADMIN.toString()))
                authorize(anyRequest, authenticated)
            }
            csrf { disable() }
            formLogin {
                loginProcessingUrl = "/login"
                usernameParameter = "email"
                passwordParameter = "pass" // あってる?
                authenticationSuccessHandler = BookManagerAuthenticationSuccessHandler()
                authenticationFailureHandler = BookManagerAuthenticationFailureHandler()
            }
            exceptionHandling {
                authenticationEntryPoint = BookManagerAuthenticationEntryPoint()
                accessDeniedHandler = BookManagerAccessDeniedHandler()
            }
            cors {
                configurationSource = corsConfigurationSource()
            }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return BookManagerUserDetailsService(authenticationService)
    }

    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder,
        userDetailsService: UserDetailsService
    ): AuthenticationManager {
        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        builder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
        return builder.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration().apply {
            addAllowedMethod(CorsConfiguration.ALL)
            addAllowedHeader(CorsConfiguration.ALL)
            addAllowedOrigin("http://localhost:8080")
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
