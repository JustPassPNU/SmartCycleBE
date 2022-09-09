package com.example.SmartCycle.products.token

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import com.example.SmartCycle.products.entities.User
import org.springframework.beans.factory.annotation.Value
import java.time.Duration
import java.util.*

@Service
class TokenService {

    @Value("\${smartcycle.jwt.signingkey}")
    private lateinit var signingkey: String

    fun generateToken(user: User): String {
        val nowDate = Date()

        val jwtToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer("SmartCycle")
            .setIssuedAt(nowDate)
            .setExpiration(Date(nowDate.time + Duration.ofMinutes(30).toMillis()))
            .claim("id", user.id)
            .claim("nickname", user.nickName)
            .signWith(SignatureAlgorithm.HS256, signingkey)
            .compact()

        return jwtToken
    }

    fun verifyToken(token: String): Pair<String, String> {

        val realToken = token.split(" ")[1]
        println(signingkey)
        val parseToken = Jwts.parser().setSigningKey(signingkey).parseClaimsJws(realToken)

        val id = parseToken.body["id"].toString()
        val nickname = parseToken.body["nickname"].toString()

        return Pair(id, nickname)

    }

}