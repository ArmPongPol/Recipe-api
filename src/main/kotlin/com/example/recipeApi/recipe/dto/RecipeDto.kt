package com.example.recipeApi.recipe.dto

import com.example.recipeApi.user.dto.UserDto
import java.util.Date

data class RecipeDto(
  val id: Long,
  val name: String,
  val description: String? = null,
  val imageUrl: String? = null,
  val user: UserDto? = null,
  val createDate: Date,
  val modifiedDate: Date,
)
