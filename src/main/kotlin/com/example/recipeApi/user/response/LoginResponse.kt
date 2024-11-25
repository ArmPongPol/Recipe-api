package com.example.recipeApi.user.response

data class LoginResponse(
  val token: String,
  val expirationTime: Long
)
