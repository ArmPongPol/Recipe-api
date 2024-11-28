package com.example.recipeApi.recipe.repository

import com.example.recipeApi.entity.UserRatingRecipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRatingRecipeRepository: JpaRepository<UserRatingRecipe, Long> {
  fun countByRecipeId(recipeId: Long): Int
  @Query("SELECT SUM(rating) FROM UserRatingRecipe WHERE recipeId = :recipeId")
  fun sumRatingByRecipeId(recipeId: Long): Double
}