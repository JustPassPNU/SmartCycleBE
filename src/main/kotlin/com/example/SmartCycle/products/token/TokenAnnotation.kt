package com.example.SmartCycle.products.token

import com.example.SmartCycle.products.entities.User
import com.example.SmartCycle.products.user.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Target(AnnotationTarget.VALUE_PARAMETER) // 생성자 매개변수에 적용
@Retention(AnnotationRetention.RUNTIME)
annotation class ValidUser()

@Component
class LoginUserArgumentResolver(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(ValidUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        httpRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User? {
        val httpServletRequest: HttpServletRequest =
            httpRequest.getNativeRequest(HttpServletRequest::class.java) as HttpServletRequest
        // Http 요청에서 토큰추출
        val token = TokenExtractor.extract(httpServletRequest)
        // 토큰이 존재하는 경우
        token?.let {
            if (tokenService.verifyToken(token)) { // 토큰검증
                val userInfo = tokenService.getUserFromToken(token) // 검증된 토큰에서 id값을 뽑아서 User를 조회
                return userRepository.findFirstById(userInfo!!.first)
            }
        }
        return null
    }
}