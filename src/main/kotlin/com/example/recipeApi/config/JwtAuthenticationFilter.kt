package com.example.recipeApi.config

import com.example.recipeApi.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter @Autowired constructor(
  private val jwtUtil: JwtUtil,
  private val userDetailsService: UserDetailsService
): OncePerRequestFilter() {
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val header = request.getHeader("Authorization")
    if (header != null && header.startsWith("Bearer ")) {
      val jwt = header.substring(7)
      val username = jwtUtil.extractUsername(jwt)

      if (username != null && SecurityContextHolder.getContext().authentication == null) {
        val userDetails = userDetailsService.loadUserByUsername(username)
        if (jwtUtil.validateToken(jwt, userDetails)) {
          val authToken = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
          )
          authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
          SecurityContextHolder.getContext().authentication = authToken
        }
      }
    }
    filterChain.doFilter(request, response)
  }
}