package com.example.kotlinSpringBootSample.config

import com.example.kotlinSpringBootSample.jooq.authentication.BookManagerAccessDeniedHandler
import com.example.kotlinSpringBootSample.jooq.authentication.BookManagerAuthenticationEntryPoint
import com.example.kotlinSpringBootSample.jooq.authentication.BookManagerAuthenticationFailureHandler
import com.example.kotlinSpringBootSample.jooq.authentication.BookManagerAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

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
                authorize("/book/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            // MEMO: GETの場合は問題ないのか、ここももしかしたら設定で変えられるかもしれないね
            // Access denied for user: Could not verify the provided CSRF token because no token was found to compare.
            // csrfを無効化しないと、POSTリクエストが403エラーになる。けど403エラーの確認方法がわからぬ
            csrf { disable() }
            formLogin {
                loginProcessingUrl = "/login"
                usernameParameter = "email"
                passwordParameter = "password"
                authenticationSuccessHandler = BookManagerAuthenticationSuccessHandler()
                authenticationFailureHandler = BookManagerAuthenticationFailureHandler()
            }
            exceptionHandling {
                // authenticationEntryPointを有効にするとauthorizeに設定していないrouteは401エラーになるのでログイン画面にリダイレクトさせる実装は必要になるかも
                // authenticationEntryPoint = BookManagerAuthenticationEntryPoint()
                accessDeniedHandler = BookManagerAccessDeniedHandler()
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


// package com.example.kotlinSpringBootSample.config
//
// import com.example.kotlinSpringBootSample.jooq.book.*
// import com.example.kotlinSpringBootSample.jooq.book.domain.RoleType
// import org.springframework.context.annotation.Bean
// import org.springframework.security.authentication.AuthenticationManager
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
// import org.springframework.security.config.annotation.web.builders.HttpSecurity
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
// import org.springframework.security.config.annotation.web.invoke
// import org.springframework.security.core.userdetails.UserDetailsService
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
// import org.springframework.security.crypto.password.PasswordEncoder
// import org.springframework.security.web.SecurityFilterChain
// import org.springframework.web.cors.CorsConfiguration
// import org.springframework.web.cors.CorsConfigurationSource
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//
// @EnableWebSecurity
// class SecurityConfig(private val authenticationService: AuthenticationService) {
//
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http {
//            authorizeHttpRequests {
//                authorize("/login", permitAll)
//                authorize("/admin/**", hasAuthority(RoleType.ADMIN.toString()))
//                authorize(anyRequest, authenticated)
//            }
//            csrf { disable() }
//            formLogin {
//                loginProcessingUrl = "/login"
//                usernameParameter = "email"
//                passwordParameter = "pass" // あってる?
//                authenticationSuccessHandler = BookManagerAuthenticationSuccessHandler()
//                authenticationFailureHandler = BookManagerAuthenticationFailureHandler()
//            }
//            exceptionHandling {
//                authenticationEntryPoint = BookManagerAuthenticationEntryPoint()
//                accessDeniedHandler = BookManagerAccessDeniedHandler()
//            }
//            cors {
//                configurationSource = corsConfigurationSource()
//            }
//        }
//        return http.build()
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        return BookManagerUserDetailsService(authenticationService)
//    }
//
//    @Bean
//    fun authenticationManager(
//        http: HttpSecurity,
//        passwordEncoder: PasswordEncoder,
//        userDetailsService: UserDetailsService
//    ): AuthenticationManager {
//        val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
//        builder.userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder)
//        return builder.build()
//    }
//
//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val corsConfiguration = CorsConfiguration().apply {
//            addAllowedMethod(CorsConfiguration.ALL)
//            addAllowedHeader(CorsConfiguration.ALL)
//            addAllowedOrigin("http://localhost:8080")
//            allowCredentials = true
//        }
//
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", corsConfiguration)
//        return source
//    }
// }