package com.example.recipeApi.user.dto

import com.querydsl.core.annotations.QueryProjection
import java.io.Serializable

data class UserDto
@QueryProjection
constructor(
  val id: Long,
  val name: String,
  val username: String,
  val email: String,
): Serializable
