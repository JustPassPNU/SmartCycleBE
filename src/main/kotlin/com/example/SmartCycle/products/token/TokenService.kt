package com.example.SmartCycle.products.token

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import com.example.SmartCycle.products.entities.User
import io.jsonwebtoken.JwtException
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
            .setExpiration(Date(nowDate.time + Duration.ofMinutes(1440).toMillis()))
            .claim("id", user.id)
            .claim("nickname", user.nickName)
            .signWith(SignatureAlgorithm.HS256, signingkey)
            .compact()

        return jwtToken
    }

    fun getUserFromToken(token: String): Pair<String, String>? {

        return try {
            val parseToken = Jwts.parser().setSigningKey(signingkey).parseClaimsJws(token)

            val id = parseToken.body["id"].toString()
            val nickname = parseToken.body["nickname"].toString()
            Pair(id, nickname)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun verifyToken(token: String): Boolean {
        return try {
            val userInfo = Jwts.parser().setSigningKey(signingkey).parseClaimsJws(token).body
            val expiration = userInfo.expiration
            expiration.after(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}