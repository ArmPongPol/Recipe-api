package com.example.recipeApi.recipe.service

import com.example.recipeApi.entity.User
import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.request.RecipeRatingRequest
import com.example.recipeApi.recipe.request.RecipeRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecipeService {
  fun createRecipe(request: RecipeRequest): RecipeDto
  fun getRecipeById(recipeId: Long): RecipeDto
  fun getAllPublishedRecipes(pageable: Pageable, search: String?): Page<RecipeDto>
  fun ratingRecipe(request: RecipeRatingRequest, recipeId: Long): Boolean
}