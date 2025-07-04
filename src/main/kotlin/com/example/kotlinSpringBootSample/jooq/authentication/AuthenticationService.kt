package com.example.kotlinSpringBootSample.jooq.authentication

import com.example.kotlinSpringBootSample.jooq.user.RoleType
import com.example.kotlinSpringBootSample.jooq.user.User
import com.example.kotlinSpringBootSample.jooq.user.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository) {
    fun findUser(email: String): User? {
        return userRepository.find(email)
    }
}

// MEMO: @Serviceを追加してコンポーネント登録していなかったためエラーになっていた
@Service
class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        println("loadUserByUsername")
        val user = authenticationService.findUser(username) ?: throw UsernameNotFoundException("User not found")
        return BookManagerUserDetails(user)
    }
}

data class BookManagerUserDetails(
    val id: Int,
    val email: String,
    @JvmField // https://youtrack.jetbrains.com/issue/KT-24822/Unexpected-platform-declaration-clash
    val password: String,
    val name: String,
    val roleType: RoleType
) : UserDetails {
    constructor(user: User) : this(user.id, user.email, user.password, user.name, user.roleType)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }
    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = this.email
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getPassword(): String = this.password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}

class BookManagerAuthenticationSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        response.status = HttpServletResponse.SC_OK
        // 追加
        val userDetails = authentication.principal as BookManagerUserDetails
        println("Login successful for user: ${userDetails.username}")
    }
}

class BookManagerAuthenticationFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        // 追加
        println("Login failed for user: ${exception.message}")
    }
}

class BookManagerAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        // 追加
        println("Authentication entry point triggered for user: ${authException.message}")
    }
}

class BookManagerAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: org.springframework.security.access.AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        // 追加
        println("Access denied for user: ${accessDeniedException.message}")
    }
}
