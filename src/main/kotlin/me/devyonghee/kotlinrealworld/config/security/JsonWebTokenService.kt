package me.devyonghee.kotlinrealworld.config.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JsonWebTokenService(
    @Value("\${jwt.secret}") secret: String,
) {
    private val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    fun token(email: String): String {
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date.from(tomorrow()))
            .signWith(key)
            .compact()
    }

    fun email(token: String): String {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (e: JwtException) {
            throw BadCredentialsException("Invalid token")
        }
    }

    private fun tomorrow(): Instant = Instant.now().plus(1, ChronoUnit.DAYS)
}
