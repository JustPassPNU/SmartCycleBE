package com.example.SmartCycle.products.user

import org.springframework.stereotype.Service
import com.example.SmartCycle.products.dto.LoginDto
import com.example.SmartCycle.products.dto.RegisterDto
import com.example.SmartCycle.products.entities.Token
import com.example.SmartCycle.products.entities.User
import com.example.SmartCycle.products.exception.NotFoundException
import com.example.SmartCycle.products.token.TokenRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.lang.IllegalStateException
import java.util.*
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
) {


    fun register(registerDto: RegisterDto): String {

        println(registerDto)
        val duplicationId = userRepository.findFirstById(registerDto.id)
        println(registerDto)

        if (duplicationId != null)
            throw IllegalStateException("이미 존재하는 id입니다.")

        println(registerDto)

        val user = User(
            id = registerDto.id,
            pw = passwordEncoder.encode(registerDto.pw),
            name = registerDto.name,
            email = registerDto.email,
        )

        userRepository.save(user)

        val token = UUID.randomUUID().toString()
        tokenRepository.save(Token(token, user))

        return token


    }

    fun findById(id: String): User? {
        return userRepository.findFirstById(id)
    }

    fun login(loginDto: LoginDto): User? {

        val user = userRepository.findFirstById(loginDto.id) ?: return null

        if (!passwordEncoder.matches(loginDto.pw, user.pw))
            return null
        return user
    }

    @Transactional
    fun userConfirm(token: String): User {
        val tokenUser = tokenRepository.findFirstById(token)
            ?: throw NotFoundException("인증코드가 올바르지 않습니다.")

        val user = tokenUser.user
        user.state = User.STATE.YES

        userRepository.save(user)

        tokenRepository.deleteById(token)

        return user
    }

}