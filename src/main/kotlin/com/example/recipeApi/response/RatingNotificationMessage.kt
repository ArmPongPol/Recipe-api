package com.example.recipeApi.response

data class RatingNotificationMessage(
  val userId: Long,
  val rating: Int,
  val recipeId: Long
)
