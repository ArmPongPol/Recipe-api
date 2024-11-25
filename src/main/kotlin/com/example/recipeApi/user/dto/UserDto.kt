package com.example.recipeApi.user.dto

data class UserDto(
  val id: Long? = null,
  val name: String,
  val username: String,
  val email: String,
  val role: String,
)
