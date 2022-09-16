package com.example.SmartCycle.products.token

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenVerifyInterceptor(
    private val tokenService: TokenService
): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token: String? = TokenExtractor.extract(request)
        token?.let {
            tokenService.verifyToken(it)
        } ?: throw AuthenticationException("인증실패")

        return true
    }
}