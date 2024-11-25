package com.example.recipeApi.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtUtil {
  @Value("\${spring.security.jwt.secret-key}")
  private var secretKey: String = ""
  @Value("\${spring.security.jwt.expiration-time}")
  private var expiration: Long? = null

  fun extractUsername(token: String): String? {
    return extractClaim(token, Claims::getSubject)
  }

  fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
    val claims = extractAllClaims(token)
    return claimsResolver.invoke(claims)
  }

  private fun extractAllClaims(token: String): Claims {
    return Jwts.parserBuilder()
      .setSigningKey(secretKey.toByteArray())
      .build()
      .parseClaimsJws(token)
      .body
  }

  fun generateToken(userDetails: UserDetails): String {
    val claims = HashMap<String, Any>()
    return createToken(claims, userDetails.username)
  }

  private fun createToken(claims: Map<String, Any>, subject: String): String {
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(Date(System.currentTimeMillis()))
      .setExpiration(Date(System.currentTimeMillis() + expiration!!))
      .signWith(SignatureAlgorithm.HS256, secretKey.toByteArray())
      .compact()
  }

  fun validateToken(token: String, userDetails: UserDetails): Boolean {
    val username = extractUsername(token)
    return username == userDetails.username && !isTokenExpired(token)
  }

  private fun isTokenExpired(token: String): Boolean {
    return extractClaim(token, Claims::getExpiration).before(Date())
  }

  fun getExpirationDateFromToken(token: String): Date {
    return extractClaim(token, Claims::getExpiration)
  }
}