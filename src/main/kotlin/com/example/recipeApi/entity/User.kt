package com.example.recipeApi.entity

import com.example.recipeApi.user.dto.UserDto
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  val username: String,
  val password: String,
  val email: String,
  @Enumerated(EnumType.STRING)
  val role: UserRole = UserRole.USER
) {
  fun toUserDto(): UserDto {
    return UserDto(
      id = id,
      name = name,
      username = username,
      email = email,
      role = role.toString()
    )
  }
}
