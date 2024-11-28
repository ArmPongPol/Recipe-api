package com.example.recipeApi.recipe.dto

import com.example.recipeApi.user.dto.UserDto
import com.querydsl.core.annotations.QueryProjection
import java.io.Serializable
import java.util.Date

data class RecipeDto
@QueryProjection
constructor(
  val id: Long,
  val name: String,
  val description: String? = null,
  val imageUrl: String? = null,
  val user: UserDto? = null,
  val rating: Double? = null,
  val isPublished: Boolean,
  val createDate: Date,
  val modifiedDate: Date,
): Serializable
