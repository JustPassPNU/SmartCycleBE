package com.example.SmartCycle.products.user

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import com.example.SmartCycle.products.dto.LoginDto
import com.example.SmartCycle.products.dto.LoginResponse
import com.example.SmartCycle.products.dto.RegisterDto
import com.example.SmartCycle.products.dto.ResponseMessage
import com.example.SmartCycle.products.entities.User
import com.example.SmartCycle.products.exception.NotFoundException
import com.example.SmartCycle.products.mail.MailService
import com.example.SmartCycle.products.token.TokenService
import com.example.SmartCycle.products.token.ValidUser
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
class UserController(
    private val userService: UserService,
    private val tokenService: TokenService,
    private val mailService: MailService,
) {

    @PostMapping("user/register")
    @Transactional
    fun register(
        @RequestBody
        @Valid
        registerDto: RegisterDto
    ): ResponseMessage {

        val authToken = userService.register(registerDto)
        mailService.sendMail(authToken, registerDto.email)
        return ResponseMessage(
            result = true,
            message = null,
            data = null
        )
    }

    @GetMapping("user/confirm/{token}")
    fun userConfirm(@PathVariable token: String): ResponseMessage {

        val responseMessage = try {
            val user = userService.userConfirm(token)
            ResponseMessage(
                result = true,
                message = "인증 성공",
                data = user
            )

        } catch (e: NotFoundException) {
            ResponseMessage(
                result = false,
                message = e.message,
                data = null
            )
        }

        return responseMessage

    }

    @GetMapping("user/{id}")
    fun findById(@PathVariable id: String): ResponseMessage {

        val user = userService.findById(id)
        var result = true

        if (user == null)
            result = false

        return ResponseMessage(
            result = result,
            message = null,
            data = user
        )
    }

    @PostMapping("user/login")
    fun login(
        @RequestBody
        @Valid
        loginDto: LoginDto
    ): ResponseEntity<ResponseMessage> {

        val user = userService.login(loginDto)

        val responseHeaders = HttpHeaders()

        if (user == null) {
            return ResponseEntity(
                ResponseMessage(
                    result = true,
                    message = "fail",
                    data = null
                ),
                responseHeaders,
                HttpStatus.FORBIDDEN
            )
        }

        val jwtToken = tokenService.generateToken(user)

        return ResponseEntity<ResponseMessage>(
            ResponseMessage(
                result = true,
                message = null,
                data = LoginResponse(
                    user = user,
                    token = "Bearer $jwtToken"
                )
            ),
            responseHeaders,
            HttpStatus.CREATED
        )
    }

    @GetMapping("user/verify")
    fun userVerify(
        @ValidUser user: User?
    ): ResponseMessage {

        return ResponseMessage(
                result = true,
                message = "nice",
                data = user
            )
    }
}