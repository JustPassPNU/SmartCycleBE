package com.example.SmartCycle.products.exception

import com.example.SmartCycle.products.dto.ResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class InterceptorExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    protected fun handleException(e: Exception) {
        e.printStackTrace()
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    protected fun handleValidException(e: MethodArgumentNotValidException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ResponseMessage(
                    result = false,
                    message = e.bindingResult.fieldErrors[0].defaultMessage,
                    data = null
                )
            )
    }

    @ExceptionHandler(value = [IllegalStateException::class])
    protected fun handleIllegalException(e: IllegalStateException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ResponseMessage(
                    result = false,
                    message = e.message,
                    data = null
                )
            )
    }

    @ExceptionHandler(value = [InterceptorException::class])
    protected fun handleBaseException(e: InterceptorException): ResponseEntity<ResponseMessage> {
        e.printStackTrace()
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