package com.example.recipeApi.config

import com.example.recipeApi.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService @Autowired constructor(
  private val userRepository: UserRepository
): UserDetailsService {
  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepository.findByUsername(username)

    return User.builder()
      .username(user.username)
      .password(user.password)
      .roles(user.role.toString())
      .build()
  }
}