package com.example.SmartCycle.products.exception

import com.example.SmartCycle.products.dto.ResponseMessage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class InterceptorExceptionHandler {
    @ExceptionHandler(value = [InterceptorException::class])
    protected fun handleBaseException(e: InterceptorException): ResponseEntity<ResponseMessage> {
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(
                ResponseMessage(
                    result = false,
                    message = e.baseResponseCode.message,
                    data = null
                )
            )
    }
}