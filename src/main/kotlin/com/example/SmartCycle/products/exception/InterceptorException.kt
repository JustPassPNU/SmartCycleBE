package com.example.SmartCycle.products.exception

import org.springframework.http.HttpStatus

class InterceptorException(val baseResponseCode: BaseResponseCode) : RuntimeException() {
}

enum class BaseResponseCode(status: HttpStatus, message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰이 유효하지않습니다."),
    OK(HttpStatus.OK, "요청 성공");

    public val status: HttpStatus = status
    public val message: String = message
}