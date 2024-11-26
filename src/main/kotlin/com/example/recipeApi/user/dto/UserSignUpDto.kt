package com.example.recipeApi.user.dto

import com.querydsl.core.annotations.QueryProjection
import java.io.Serializable

data class UserSignUpDto
@QueryProjection
constructor (
  val username: String,
  val password: String,
): Serializable
