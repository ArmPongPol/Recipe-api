package com.example.recipeApi.user.service.impl

import com.example.recipeApi.entity.User
import com.example.recipeApi.user.dto.UserDto
import com.example.recipeApi.user.dto.UserSignUpDto
import com.example.recipeApi.user.repository.UserRepository
import com.example.recipeApi.user.request.UserSignInRequest
import com.example.recipeApi.user.request.UserSignUpRequest
import com.example.recipeApi.user.response.LoginResponse
import com.example.recipeApi.user.service.UserService
import com.example.recipeApi.util.JwtUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor (
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val authenticationManager: AuthenticationManager,
  private val userDetailsService: UserDetailsService,
  private val jwtUtil: JwtUtil,
): UserService {
  override fun signUp(request: UserSignUpRequest): UserSignUpDto {
    val userData = userRepository.save(
      User(
        name = request.name,
        email = request.email,
        username = request.username,
        password = passwordEncoder.encode(request.password)
      )
    )

    return UserSignUpDto(
      username = userData.username,
      password = userData.password
    )
  }

  override fun signIn(request: UserSignInRequest, response: HttpServletResponse): String {
    authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(request.username, request.password)
    )

    val userDetail = userDetailsService.loadUserByUsername(request.username)
    val jwt = jwtUtil.generateToken(userDetail)
    val expirationDate = jwtUtil.getExpirationDateFromToken(jwt).time

    val jwtCookie = ResponseCookie.from("cookie", jwt)
      .httpOnly(true)
      .maxAge(expirationDate)
      .path("/")
      .build()

    response.addHeader("Set-Cookie", jwtCookie.toString())

    return "Login Successful"
  }

  override fun getAllUsers(): List<UserDto> {
    return userRepository.findAll().map {
      it.toUserDto()
    }
  }


}