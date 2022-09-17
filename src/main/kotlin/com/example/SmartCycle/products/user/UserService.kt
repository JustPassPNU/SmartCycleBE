package com.example.SmartCycle.products.user

import org.springframework.stereotype.Service
import com.example.SmartCycle.products.dto.LoginDto
import com.example.SmartCycle.products.dto.RegisterDto
import com.example.SmartCycle.products.entities.Token
import com.example.SmartCycle.products.entities.User
import com.example.SmartCycle.products.exception.NotFoundException
import com.example.SmartCycle.products.token.TokenRepository
import java.lang.IllegalStateException
import java.util.*
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {


    fun register(registerDto: RegisterDto): String {

        val duplicationId = userRepository.findFirstById(registerDto.id)

        if (duplicationId != null)
            throw IllegalStateException("이미 존재하는 id입니다.")

        val user = User(
            id = registerDto.id,
            pw = registerDto.pw,
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

        if (user.pw != loginDto.pw)
            return null
        return user

    }

    @Transactional
    fun userConfirm(token: String): User {
        val tokenUser = tokenRepository.findFirstById(token)
            ?: throw NotFoundException("토큰에 해당하는 유저를 찾지 못함")

        val user = tokenUser.user
        user.state = User.STATE.YES

        userRepository.save(user)

        tokenRepository.deleteById(token)

        return user
    }

}