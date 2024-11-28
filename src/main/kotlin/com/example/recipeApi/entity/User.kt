package com.example.recipeApi.entity

import com.example.recipeApi.user.dto.UserDto
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  var name: String,
  var username: String,
  val password: String,
  val email: String,
  @Enumerated(EnumType.STRING)
  val role: UserRole = UserRole.USER,

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  val userRatingRecipes: List<UserRatingRecipe> = emptyList(),
) {
  fun toUserDto(): UserDto {
    return UserDto(
      id = id,
      name = name,
      username = username,
      email = email,
    )
  }
}
