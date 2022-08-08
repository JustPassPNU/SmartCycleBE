package com.example.SmartCycle.products.service

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import com.example.SmartCycle.products.entities.User
import java.time.Duration
import java.util.*

@Service
class TokenService(

) {

    fun generateToken(user: User): String {
        val nowDate = Date()

        val jwtToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer("SmartCycle")
            .setIssuedAt(nowDate)
            .setExpiration(Date(nowDate.time + Duration.ofMinutes(30).toMillis()))
            .claim("id", user.id)
            .claim("nickname", user.nickName)
            .signWith(SignatureAlgorithm.HS256, "password")
            .compact()

        return jwtToken
    }

    fun verifyToken(token: String): Pair<String, String> {

        val realToken = token.split(" ")[1]

        val parseToken = Jwts.parser().setSigningKey("password").parseClaimsJws(realToken)

        val id = parseToken.body["id"].toString()
        val nickname = parseToken.body["nickname"].toString()

        return Pair(id, nickname)

    }

}