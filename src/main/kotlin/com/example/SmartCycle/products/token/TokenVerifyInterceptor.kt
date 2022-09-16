package com.example.SmartCycle.products.token

import com.example.SmartCycle.products.exception.BaseResponseCode
import com.example.SmartCycle.products.exception.InterceptorException
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
            if(tokenService.verifyToken(it))
                return true
        }
        throw InterceptorException(BaseResponseCode.UNAUTHORIZED)
    }
}